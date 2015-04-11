package uk.co.thefishlive.meteor.json.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import uk.co.thefishlive.auth.assessments.questions.Question;
import uk.co.thefishlive.auth.assessments.questions.QuestionType;
import uk.co.thefishlive.meteor.assessments.questions.answer.MeteorAnswerQuestion;
import uk.co.thefishlive.meteor.assessments.questions.multichoice.MeteorMultichoiceQuestion;
import uk.co.thefishlive.meteor.json.JsonAdapter;

import java.lang.reflect.Type;

/**
 *
 */
public class MeteorQuestionAdapter implements JsonAdapter<Question> {

    @Override
    public Question deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject json = jsonElement.getAsJsonObject();

        if (!json.has("type")) {
            throw new JsonParseException("Question does not contain type");
        }

        QuestionType questionType = QuestionType.values()[json.get("type").getAsInt()];
        return context.deserialize(json, getHandlerClass(questionType));
    }

    private Class<? extends Question> getHandlerClass(QuestionType type) {
        switch (type) {
            case MULTI_CHOICE:
                return MeteorMultichoiceQuestion.class;
            case ANSWER:
                return MeteorAnswerQuestion.class;
            default:
                throw new JsonParseException("Invalid question type");
        }
    }

    @Override
    public JsonElement serialize(Question question, Type type, JsonSerializationContext context) {
        JsonElement element = context.serialize(question, getHandlerClass((QuestionType) question.getType()));
        element.getAsJsonObject().addProperty("type", question.getType().ordinal());
        return element;
    }
}
