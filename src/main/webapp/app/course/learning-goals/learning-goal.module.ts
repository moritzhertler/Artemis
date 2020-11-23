import { NgModule } from '@angular/core';
import { ArtemisLearningGoalsRoutingModule } from 'app/course/learning-goals/learning-goal-routing.module';
import { LearningGoalFormComponent } from './learning-goal-form/learning-goal-form.component';
import { CreateLearningGoalComponent } from './create-learning-goal/create-learning-goal.component';
import { ArtemisMarkdownEditorModule } from 'app/shared/markdown-editor/markdown-editor.module';
import { ArtemisSharedModule } from 'app/shared/shared.module';
import { ArtemisSharedComponentModule } from 'app/shared/components/shared-component.module';
import { ReactiveFormsModule } from '@angular/forms';
import { EditLearningGoalComponent } from './edit-learning-goal/edit-learning-goal.component';
import { LearningGoalManagementComponent } from './learning-goal-management/learning-goal-management.component';
import { LearningGoalCardComponent } from './learning-goal-card/learning-goal-card.component';

@NgModule({
    imports: [ArtemisMarkdownEditorModule, ArtemisSharedModule, ReactiveFormsModule, ArtemisSharedComponentModule, ArtemisLearningGoalsRoutingModule],
    declarations: [LearningGoalFormComponent, CreateLearningGoalComponent, EditLearningGoalComponent, LearningGoalManagementComponent, LearningGoalCardComponent],
})
export class ArtemisLearningGoalsModule {}
