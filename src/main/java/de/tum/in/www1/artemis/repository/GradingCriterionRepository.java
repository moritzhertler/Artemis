package de.tum.in.www1.artemis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.tum.in.www1.artemis.domain.GradingCriterion;
import de.tum.in.www1.artemis.web.rest.errors.EntityNotFoundException;

/**
 * Spring Data JPA repository for the GradingCriteria entity.
 */
@Repository
public interface GradingCriterionRepository extends JpaRepository<GradingCriterion, Long> {

    List<GradingCriterion> findByExerciseId(long exerciseId);

    @Query("select distinct criterion from GradingCriterion criterion left join fetch criterion.structuredGradingInstructions where criterion.exercise.id = :#{#exerciseId}")
    List<GradingCriterion> findByExerciseIdWithEagerGradingCriteria(@Param("exerciseId") long exerciseId);

    /**
     * Get one grading criterion by gradingCriterionId.
     *
     * @param gradingCriterionId the gradingCriterionId of the entity
     * @return the entity
     */
    default GradingCriterion findOne(long gradingCriterionId) {
        return findById(gradingCriterionId).orElseThrow(() -> new EntityNotFoundException("Grading Criterion", gradingCriterionId));
    }
}
