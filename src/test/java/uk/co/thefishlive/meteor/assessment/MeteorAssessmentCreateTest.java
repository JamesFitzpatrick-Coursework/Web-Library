package uk.co.thefishlive.meteor.assessment;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import uk.co.thefishlive.auth.assessments.Assessment;
import uk.co.thefishlive.auth.assessments.AssessmentBuilder;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestion;
import uk.co.thefishlive.auth.assessments.questions.multichoice.MultichoiceQuestionBuilder;
import uk.co.thefishlive.http.exception.HttpException;
import uk.co.thefishlive.meteor.TestBase;
import uk.co.thefishlive.auth.assessments.exception.AssessmentCreateException;
import uk.co.thefishlive.auth.assessments.exception.AssessmentException;

import java.io.IOException;

import static org.junit.Assert.*;

// We have to run these tests in this exact order or they will fail
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeteorAssessmentCreateTest extends TestBase {

    private static Assessment assessment;

    @Test
    public void test01_CreateAssessment() throws IOException, AssessmentCreateException {
        AssessmentBuilder builder = authHandler.getAssessmentManager().getAssessmentFactory().createAssessmentBuilder();
        builder.setName("Test Assessment");

        MultichoiceQuestionBuilder questionBuilder1 = (MultichoiceQuestionBuilder) builder.createQuestionBuilder(QuestionType.MULTI_CHOICE);
        questionBuilder1.setQuestion("Test question 1");
        questionBuilder1.setQuestionNumber(1);
        questionBuilder1.addOption("Answer 1");
        questionBuilder1.addOption("Answer 2");
        questionBuilder1.addOption("Answer 3");
        questionBuilder1.addOption("Answer 4");
        MultichoiceQuestion question1 = (MultichoiceQuestion) questionBuilder1.build();
        question1.setCurrentAnswer(3);
        builder.addQuestion(question1);

        MultichoiceQuestionBuilder questionBuilder2 = (MultichoiceQuestionBuilder) builder.createQuestionBuilder(QuestionType.MULTI_CHOICE);
        questionBuilder2.setQuestion("Test question 2");
        questionBuilder2.setQuestionNumber(2);
        questionBuilder2.addOption("Answer 1");
        questionBuilder2.addOption("Answer 2");
        questionBuilder2.addOption("Answer 3");
        questionBuilder2.addOption("Answer 4");
        MultichoiceQuestion question2 = (MultichoiceQuestion) questionBuilder2.build();
        question2.setCurrentAnswer(1);
        builder.addQuestion(question2);

        assessment = builder.build();
        assertNotNull(assessment);
    }

    @Test
    public void test02_GetAssessment() throws IOException, AssessmentException {
        assertNotNull(authHandler.getAssessmentManager().getAssessment(assessment.getProfile()));
    }

    @Test
    public void test03_DeleteAssessment() throws IOException, AssessmentException {
        assertTrue(authHandler.getAssessmentManager().deleteAssessment(assessment));
    }

    @Test(expected = HttpException.class)
    public void test04_GetAssessment() throws IOException, AssessmentException {
        authHandler.getAssessmentManager().getAssessment(assessment.getProfile());
    }
}
