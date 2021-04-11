import { Component, Input } from '@angular/core';
import { ParticipantScoreAverageDTO } from 'app/shared/participant-scores/participant-scores.service';
import { round } from 'app/shared/util/utils';
import { BaseEntity } from 'app/shared/model/base-entity';

@Component({
    selector: 'jhi-participant-scores-average-table',
    templateUrl: './participant-scores-average-table.component.html',
})
export class ParticipantScoresAverageTableComponent {
    readonly round = round;
    @Input()
    participantAverageScores: ParticipantScoreAverageDTO[] = [];
    @Input()
    isLoading = false;

    participantAverageScoresAsBaseEntity = (this.participantAverageScores as unknown) as BaseEntity[];

    extractParticipantName = (participantScoreAverageDTO: any) => {
        return participantScoreAverageDTO.userName ? String(participantScoreAverageDTO.userName) : String(participantScoreAverageDTO.teamName);
    };
}
