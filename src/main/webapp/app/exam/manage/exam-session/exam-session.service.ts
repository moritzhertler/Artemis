import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { ExamSession } from 'app/entities/exam-session.model';

@Injectable({ providedIn: 'root' })
export class ExamSessionService {
    public resourceUrl = SERVER_API_URL + 'api/courses';

    constructor(private http: HttpClient) {}

    /**
     * Get current session token for user and given exam
     * @param courseId The course id.
     * @param examId The exam id.
     */
    getCurrentExamSession(courseId: number, examId: number): Observable<HttpResponse<ExamSession>> {
        return this.http.get<ExamSession>(`${this.resourceUrl}/${courseId}/exams/${examId}/session`, { observe: 'response' });
    }
}