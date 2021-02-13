package de.tum.in.www1.artemis.repository;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.LOAD;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.tum.in.www1.artemis.domain.Course;

/**
 * Spring Data JPA repository for the Course entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("select distinct course.teachingAssistantGroupName from Course course")
    Set<String> findAllTeachingAssistantGroupNames();

    @Query("select distinct course.instructorGroupName from Course course")
    Set<String> findAllInstructorGroupNames();

    @Query("select distinct course from Course course where course.instructorGroupName like :#{#name}")
    Course findCourseByInstructorGroupName(@Param("name") String name);

    @Query("select distinct course from Course course where course.studentGroupName like :#{#name}")
    Course findCourseByStudentGroupName(@Param("name") String name);

    @Query("select distinct course from Course course where course.teachingAssistantGroupName like :#{#name}")
    Course findCourseByTeachingAssistantGroupName(@Param("name") String name);

    @Query("select distinct course from Course course where (course.startDate <= :#{#now} or course.startDate is null) and (course.endDate >= :#{#now} or course.endDate is null)")
    List<Course> findAllActive(@Param("now") ZonedDateTime now);

    @EntityGraph(type = LOAD, attributePaths = { "lectures", "lectures.attachments", "exams" })
    @Query("select distinct course from Course course where (course.startDate <= :#{#now} or course.startDate is null) and (course.endDate >= :#{#now} or course.endDate is null)")
    List<Course> findAllActiveWithLecturesAndExams(@Param("now") ZonedDateTime now);

    @EntityGraph(type = LOAD, attributePaths = { "lectures", "lectures.attachments", "exams" })
    Optional<Course> findWithEagerLecturesAndExamsById(long courseId);

    // Note: this is currently only used for testing purposes
    @Query("select distinct course from Course course left join fetch course.exercises exercises left join fetch course.lectures lectures left join fetch lectures.attachments left join fetch exercises.categories where (course.startDate <= :#{#now} or course.startDate is null) and (course.endDate >= :#{#now} or course.endDate is null)")
    List<Course> findAllActiveWithEagerExercisesAndLectures(@Param("now") ZonedDateTime now);

    @EntityGraph(type = LOAD, attributePaths = { "exercises", "exercises.categories", "exercises.teamAssignmentConfig" })
    Course findWithEagerExercisesById(long courseId);

    @EntityGraph(type = LOAD, attributePaths = { "learningGoals" })
    Optional<Course> findWithEagerLearningGoalsById(long courseId);

    @EntityGraph(type = LOAD, attributePaths = { "exercises", "lectures" })
    Course findWithEagerExercisesAndLecturesById(long courseId);

    @EntityGraph(type = LOAD, attributePaths = { "exercises", "lectures", "lectures.lectureUnits", "learningGoals" })
    Course findWithEagerExercisesAndLecturesAndLectureUnitsAndLearningGoalsById(long courseId);

    @Query("select distinct course from Course course where (course.startDate is null or course.startDate <= :#{#now}) and (course.endDate is null or course.endDate >= :#{#now}) and course.onlineCourse = false and course.registrationEnabled = true")
    List<Course> findAllCurrentlyActiveNotOnlineAndRegistrationEnabled(@Param("now") ZonedDateTime now);

    List<Course> findAllByShortName(String shortName);

    Optional<Course> findById(long courseId);

    @Query("""
            select c.studentGroupName
            from Course c
            where c.id = :courseId
            """)
    String findStudentGroupName(@Param("courseId") long courseId);

    @Query("""
            select s.submissionDate as day, u.login as username
            from User u, Submission s, StudentParticipation p
            where s.participation.exercise.course.id = :#{#courseId} and s.participation.id = p.id and p.student.id = u.id and s.submissionDate >= :#{#startDate} and s.submissionDate <= :#{#endDate}
                and s.participation.exercise.course.studentGroupName member of u.groups
            order by s.submissionDate asc
            """)
    List<Map<String, Object>> getActiveStudents(@Param("courseId") Long courseId, @Param("startDate") ZonedDateTime startDate, @Param("endDate") ZonedDateTime endDate);

    @Query("""
            select c.id as id,
            c.title as title,
            c.testCourse as testCourse,
            c.semester as semester,
            c.shortName as shortName,
            c.color as color,
            c.studentGroupName as studentGroupName,
            c.teachingAssistantGroupName as teachingAssistantGroupName,
            c.instructorGroupName as instructorGroupName
            from Course c
            where c.endDate is null or :#{#now} is null or c.endDate >= :#{#now}
            """)
    List<Map<String, Object>> getAllDTOsForOverview(@Param("now") ZonedDateTime now);

    @Query("""
            select c.id as courseId,
            c.presentationScore as presentationScore,
            c.semester as semester,
            c.startDate as startDate,
            c.endDate as endDate,
            c.description as description,
            c.title as title,
            c.testCourse as testCourse,
            c.shortName as shortName,
            c.color as color,
            c.studentGroupName as studentGroupName,
            c.teachingAssistantGroupName as teachingAssistantGroupName,
            c.instructorGroupName as instructorGroupName
            from Course c
            where c.id = :#{#courseId}
            """)
    List<Map<String, Object>> getStatsForDetailView(@Param("courseId") Long courseId);

    @Query("""
            select c.studentGroupName as studentGroupName,
            c.teachingAssistantGroupName as teachingAssistantGroupName,
            c.instructorGroupName as instructorGroupName
            from Course c
            where c.id = :courseId
            """)
    Map<String, Object> findGroupNames(@Param("courseId") long courseId);
}
