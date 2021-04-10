import { Injectable } from '@angular/core';
import { SERVER_API_URL } from 'app/app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { Moment } from 'moment';

export class ExerciseScoresDTO {
    public exerciseId?: number;
    public exerciseTitle?: string;
    public exerciseType?: string;
    public releaseDate?: Moment;
    public scoreOfStudent?: number;
    public averageScoreAchieved?: number;
    public maxScoreAchieved?: number;
}

@Injectable({ providedIn: 'root' })
export class ExerciseScoresChartService {
    public resourceUrl = SERVER_API_URL + 'api';

    constructor(private http: HttpClient) {}

    getCourseExerciseScores(courseId: number): Observable<HttpResponse<ExerciseScoresDTO[]>> {
        return this.http
            .get<ExerciseScoresDTO[]>(`${this.resourceUrl}/courses/${courseId}/charts/exercise-scores`, { observe: 'response' })
            .map((response: HttpResponse<ExerciseScoresDTO[]>) => {
                if (response.body) {
                    for (const exerciseScoreDTO of response.body) {
                        exerciseScoreDTO.releaseDate = exerciseScoreDTO.releaseDate ? moment(exerciseScoreDTO.releaseDate) : undefined;
                    }
                }
                return response;
            });
    }
}