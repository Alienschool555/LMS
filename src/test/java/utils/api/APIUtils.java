package utils.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.microsoft.playwright.APIResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.json.JSONObject;
import org.testng.Assert;
import utils.reports.LoggerUtils;
import utils.reports.ReportUtils;

import java.util.*;

public final class APIUtils {

    static void checkStatus(APIResponse apiResponse, String methodName) {
        if (apiResponse.status() < 200 || apiResponse.status() >= 300) {
            LoggerUtils.logException("API: EXCEPTION: Request " + methodName + " FAILED." + apiResponse.status());
            Assert.fail();
        } else {
            LoggerUtils.logInfo("API: " + methodName + " " + apiResponse.status());
        }
    }

    static JsonObject initJsonObject(String apiResponseBody) {
        JsonObject object = new JsonObject();
        try {
            return new Gson().fromJson(apiResponseBody, JsonObject.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return object;
    }

    private static JsonArray initJsonArray(String apiResponseBody) {
        JsonArray array = new JsonArray();
        try {
            return new Gson().fromJson(apiResponseBody, JsonArray.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return array;
    }

    private static JsonObject getActiveCourse() {
        final APIResponse getCoursesActive = APIUserServices.getCoursesActive();
        checkStatus(getCoursesActive, "getCoursesActive");

        return initJsonObject(getCoursesActive.text());
    }

    private static JsonObject getStudyGuide(String courseId) {
        final APIResponse getGuidesCoursesId = APIUserServices.getGuidesCoursesId(courseId);
        checkStatus(getGuidesCoursesId, "getGuidesCoursesId");

        return initJsonObject(getGuidesCoursesId.text());
    }

    private static JsonObject getStudyGuideTable(String guideId) {
        final APIResponse getGuidesIdTableOfContent = APIUserServices.getGuidesIdTableOfContent(guideId);
        checkStatus(getGuidesIdTableOfContent, "getGuidesIdTableOfContent");

        return initJsonObject(getGuidesIdTableOfContent.text());
    }

    private static JsonObject getChapter(String guideTableId, String chapterId) {
        final APIResponse getGuidesIdChaptersId = APIUserServices.getGuidesIdChaptersId(guideTableId, chapterId);
        checkStatus(getGuidesIdChaptersId, "getGuidesIdChaptersId");

        return initJsonObject(getGuidesIdChaptersId.text());
    }

    private static JsonObject getPlans() {
        APIResponse getPlans = APIUserServices.getPlans();
        checkStatus(getPlans, "getPlans");

        return initJsonObject(getPlans.text());
    }

    private static JsonObject getPlanPhases(String currentPlanId) {
        APIResponse getPlansIdPhases = APIUserServices.getPlansIdPhases(currentPlanId);
        checkStatus(getPlansIdPhases, "getPlansIdPhases");

        return initJsonObject(getPlansIdPhases.text());
    }

    private static JsonObject getFlashcardsPacks(int limit) {
        APIResponse getFlashcardsPacks = APIUserServices.getFlashcardsPacks(limit);
        checkStatus(getFlashcardsPacks, "getFlashcardsPacks");

        return initJsonObject(getFlashcardsPacks.text());
    }

    private static JsonObject getFlashcardsByPack(String packId, int limit) {
        APIResponse getFlashcardsPacksCardsPackTypeId = APIUserServices.getFlashcardsPacksCardsPackTypeId(packId, limit);
        checkStatus(getFlashcardsPacksCardsPackTypeId, "getFlashcardsPacksCardsPackTypeId");

        return initJsonObject(getFlashcardsPacksCardsPackTypeId.text());
    }

    private static void saveFlashcardAnswer(String flashcardId, String answer) {
        APIResponse postFlashcardsIDAnswers = APIUserServices.postFlashcardsIDAnswers(flashcardId, answer);
        checkStatus(postFlashcardsIDAnswers, "postFlashcardsIDAnswers");
    }

    private static void adminChangeChapterText(JsonObject unit) {
        APIResponse patchAdminGuidesUnitsId = APIAdminServices.patchAdminGuidesUnitsId(unit.get("id").getAsString(), unit);
        checkStatus(patchAdminGuidesUnitsId, "patchAdminGuidesUnitsId");
    }

    private static void changeCurrentPlan(String planId) {
        APIResponse postPlansCurrent = APIUserServices.postPlansCurrent(planId);
        checkStatus(postPlansCurrent, "postPlansCurrent");
    }

    private static void markTasksById(String taskId) {
        APIResponse postTasksIdMark = APIUserServices.postTasksIdMark(taskId);
        checkStatus(postTasksIdMark, "postTasksIdMark");
    }

    private static void completePack(String packId) {
        APIResponse postFlashcardsPacksIdComplete = APIUserServices.postFlashcardsPacksIdComplete(packId);
        checkStatus(postFlashcardsPacksIdComplete, "postFlashcardsPacksIdComplete");
    }

    private static void deleteFlashcardsResults() {
        APIResponse deleteFlashcardsResults = APIUserServices.deleteFlashcardsResults();
        checkStatus(deleteFlashcardsResults, "deleteFlashcardsResults");
    }

    private static void deleteCoursesResults() {
        final APIResponse deleteCoursesResults = APIUserServices.deleteCoursesResults();
        checkStatus(deleteCoursesResults, "deleteCoursesResults");
    }

    private static JsonArray getAllDomains(String courseId) {
        final APIResponse postQuestionDomains = APIUserServices.postQuestionDomains(courseId);
        checkStatus(postQuestionDomains, "postQuestionDomains");

        return initJsonArray(postQuestionDomains.text());
    }

    private static JsonObject startTutorTest(List<String> domainsIds, int questionsAmount) {
        APIResponse postTutorTestStart = APIUserServices.postTutorTestStart(domainsIds, questionsAmount);
        checkStatus(postTutorTestStart, "postTutorTestStart");

        return initJsonObject(postTutorTestStart.text());
    }

    private static JsonObject postIncorrectAnswer(String testId, String questionId, String answerId) {
        APIResponse postPassagesIDAnswer = APIUserServices.postPassagesIDAnswer(testId, questionId, answerId);
        checkStatus(postPassagesIDAnswer, "postPassagesIDAnswer");

        return initJsonObject(postPassagesIDAnswer.text());
    }

    private static JsonObject finishTest(String testId) {
        APIResponse postPassagesIdFinish = APIUserServices.postPassagesIdFinish(testId);
        checkStatus(postPassagesIdFinish, "postPassagesIdFinish");

        return initJsonObject(postPassagesIdFinish.text());
    }

    private static void deletePaymentMethod() {
        final int status = APIUserServices.deleteCustomerPaymentMethod().status();
        switch (status) {
            case 401 -> LoggerUtils.logError("API: ERROR: Unauthorized " + status);
            case 422 -> LoggerUtils.logInfo("API: Payment method NOT found " + status);
            case 204 -> LoggerUtils.logInfo("API: deleteCustomerPaymentMethod " + status);
            default -> LoggerUtils.logException("API: EXCEPTION: Request FAILED deleteCustomerPaymentMethod " + status);
        }
    }

    private static String getPlanId(JsonObject plans, String planName) {
        try {
            JsonArray plansArray = plans.getAsJsonArray("plans");

            for (JsonElement planElement : plansArray) {
                JsonObject plan = planElement.getAsJsonObject();
                String name = plan.get("name").getAsString();

                if (name.equals(planName)) {
                    return plan.get("id").getAsString();
                }
            }
        } catch (Exception e) {
            LoggerUtils.logException("API: EXCEPTION: FAILED extract id from " + planName + " plan");
        }

        return "";
    }

    private static List<String> getTasksIds(JsonObject planPhases) {
        List<String> tasksIds = new ArrayList<>();
        try {
            JsonArray items = planPhases.getAsJsonArray("items");

            for (JsonElement itemElement : items) {
                JsonObject item = itemElement.getAsJsonObject();
                JsonArray tasks = item.getAsJsonArray("tasks");

                for (JsonElement taskElement : tasks) {
                    String id = taskElement.getAsJsonObject().get("id").getAsString();
                    tasksIds.add(id);
                }
            }
        } catch (Exception e) {
            LoggerUtils.logException("API: EXCEPTION: FAILED to extract tasks ids from plan phases.");
        }

        return tasksIds;
    }

    private static void markTasks(List<String> tasksIds) {
        for (String id : tasksIds) {
            markTasksById(id);
        }
    }

    private static String getCourseName(String courseId) {
        if (courseId.equals(APIData.GOLD_SUBSCRIPTION_ID)) {
            return "GOLD";
        } else if (courseId.equals(APIData.BRONZE_SUBSCRIPTION_ID)) {
            return "BRONZE";
        } else {
            return "Unknown Subscription";
        }
    }

    private static void setActiveCourse(String courseId) {
        APIResponse response = APIUserServices.postCoursesIdSetActive(courseId);

        if (response.status() == 201) {
            LoggerUtils.logInfo("API: Switched to " + getCourseName(courseId) + " subscription");
        } else {
            LoggerUtils.logError("API: EXCEPTION: Request FAILED postCoursesIdSetActive " + response.status());
            Assert.fail();
        }
    }

    public static String getActiveCourseId() {
        APIResponse getCoursesActive = APIUserServices.getCoursesActive();
        checkStatus(getCoursesActive, "getCoursesActive");

        JSONObject object = new JSONObject(getCoursesActive.text());

        return object.getString("id");
    }

    public static void cleanUserData() {
        deleteCoursesResults();
        deletePaymentMethod();
    }

    @Step("API: ADMIN: Change Chapter1 Unit1 Text - {action} '{word}'.")
    public static void adminChangeChapter1Unit1Text(String word, String action) {
        final String courseId = getActiveCourse().get("id").getAsString();
        final String studyGuideId = getStudyGuide(courseId).get("id").getAsString();
        final JsonObject guideTable = getStudyGuideTable(studyGuideId);

        final JsonObject chapter = guideTable.getAsJsonArray("chapters").get(0).getAsJsonObject();

        final String guideTableId = guideTable.get("id").getAsString();
        final String chapterId = chapter.get("id").getAsString();

        final JsonObject chapter1 = getChapter(guideTableId, chapterId);

        final JsonObject unit1 = chapter1.getAsJsonArray("units").get(0).getAsJsonObject();

        String unit1Text = unit1
                .getAsJsonObject("content")
                .getAsJsonArray("blocks")
                .get(0).getAsJsonObject()
                .getAsJsonObject("data")
                .get("text").getAsString();

        switch (action) {
            case "add" -> unit1Text = word + unit1Text;
            case "remove" -> unit1Text = unit1Text.substring(unit1Text.indexOf(word) + unit1Text.length());
        }

        unit1.getAsJsonObject("content")
                .getAsJsonArray("blocks")
                .get(0).getAsJsonObject()
                .getAsJsonObject("data")
                .addProperty("text", unit1Text);

        adminChangeChapterText(unit1);
    }

    @Step("API: Mark tasks (activate checkboxes) for {planName} study plan.")
    public static void markTasks(String planName) {
        final JsonObject plans = getPlans();
        final String planId = getPlanId(plans, planName);

        changeCurrentPlan(planId);

        final JsonObject planPhases = getPlanPhases(planId);
        final List<String> tasksIds = getTasksIds(planPhases);

        if (tasksIds.isEmpty()) {
            LoggerUtils.logError("API: ERROR: Tasks ids list is EMPTY.");
            Assert.fail();
        }

        markTasks(tasksIds);
    }

    public static void activateBronzeSubscriptionCourse() {
        APIResponse response = APIUserServices.postCoursesIdSubscribe(
                APIData.BRONZE_SUBSCRIPTION_ID, APIData.MONTHLY, APIData.BRONZE
        );
        int status = response.status();

        switch (status) {
            case 201 -> {
                LoggerUtils.logInfo("API: 'TEST AUTOMATION _DO NOT DELETE_BRONZE' is active");
                Allure.step("Precondition: Active course is under the BRONZE plan.");
            }
            case 422 -> {
                LoggerUtils.logInfo("API: 'TEST AUTOMATION _DO NOT DELETE_BRONZE' course is exists");
                setActiveCourse(APIData.BRONZE_SUBSCRIPTION_ID);
                LoggerUtils.logInfo("API: Switched to 'TEST AUTOMATION _DO NOT DELETE_BRONZE'");
            }
            default -> LoggerUtils.logError("API: ERROR: " + status + response.statusText());
        }
    }

    public static void activateGoldSubscription() {
        String activeCourseId = getActiveCourseId();

        if (!APIData.GOLD_SUBSCRIPTION_ID.equals(activeCourseId)) {
            setActiveCourse(APIData.GOLD_SUBSCRIPTION_ID);
        }

        LoggerUtils.logInfo("API: The active course is on the GOLD plan." + ReportUtils.getEndLine());
    }

    public static void setMarkOptionsForFlashcardPacks(String[] packsNames, int limit) {
        final JsonArray allPacks = getFlashcardsPacks(limit).getAsJsonArray("items");

        for (JsonElement packElement : allPacks) {
            final JsonObject pack = packElement.getAsJsonObject();
            final String packName = pack.get("name").getAsString();
            final String packId = pack.get("id").getAsString();

            if (packsNames.length != 0 && Arrays.asList(packsNames).contains(packName) && allPacks.size() != 0) {
                JsonArray flashcards = getFlashcardsByPack(packId, limit).getAsJsonArray("items");

                final List<List<Integer>> parts = getParts(flashcards);
                assert parts != null;
                if (parts.size() == 3) {
                    for (int index : parts.get(0)) {
                        String flashcardId = flashcards.get(index).getAsJsonObject().get("id").getAsString();
                        saveFlashcardAnswer(flashcardId, APIData.answerStatus.YES.name());
                    }
                    for (int index : parts.get(1)) {
                        String flashcardId = flashcards.get(index).getAsJsonObject().get("id").getAsString();
                        saveFlashcardAnswer(flashcardId, APIData.answerStatus.NO.name());
                    }
                    for (int index : parts.get(2)) {
                        String flashcardId = flashcards.get(index).getAsJsonObject().get("id").getAsString();
                        saveFlashcardAnswer(flashcardId, APIData.answerStatus.KINDA.name());
                    }

                    completePack(pack.get("id").getAsString());
                }
            }
        }
    }

    private static List<List<Integer>> getParts(JsonArray flashcards) {
        if (flashcards.size() != 0) {
            List<Integer> part1 = new ArrayList<>();
            List<Integer> part2 = new ArrayList<>();
            List<Integer> part3 = new ArrayList<>();
            List<List<Integer>> result = new ArrayList<>();

            if (flashcards.size() > 3) {
                int cardsAmount = flashcards.size() / 4;
                for (int i = 0; i < cardsAmount; i++) {
                    part1.add(i);
                }
                for (int i = cardsAmount; i < (2 * cardsAmount); i++) {
                    part2.add(i);
                }
                for (int i = 2 * cardsAmount; i < 3 * cardsAmount; i++) {
                    part3.add(i);
                }
                result.add(part1);
                result.add(part2);
                result.add(part3);

                return result;
            } else {
                for (int i = 0; i < flashcards.size(); i++) {
                    part1.add(i);
                }

                result.add(part1);
            }
        }

        return null;
    }

    private static List<String> getDomainsIds(int totalQuestions) {
        final String courseId = getActiveCourseId();
        final JsonArray questionDomains = getAllDomains(courseId);

        List<String> domainsIds = new ArrayList<>();
        for (JsonElement domain : questionDomains) {
            JsonObject eachDomainAll = domain.getAsJsonObject().get("all").getAsJsonObject();
            if (eachDomainAll.get("total").getAsInt() >= totalQuestions) {
                domainsIds.add(eachDomainAll.get("id").getAsString());
            }
        }

        if (domainsIds.isEmpty()) {
            LoggerUtils.logError("API: ERROR: Domains Ids List is EMPTY.");
            Assert.fail();
        }

        return domainsIds;
    }

    public static void answerIncorrectAndFinish(int totalQuestionsPerDomain, int questionsAmount) {
        final List<String> domainsIds = getDomainsIds(totalQuestionsPerDomain);
        final JsonObject tutorTest = startTutorTest(domainsIds, questionsAmount);
        final String testId = tutorTest.getAsJsonObject().get("id").getAsString();
        final JsonArray questionsArray = tutorTest.getAsJsonArray("questions");

        for(JsonElement question : questionsArray) {
            String questionId = question.getAsJsonObject().get("id").getAsString();
            JsonArray options = question.getAsJsonObject().getAsJsonArray("options");

            for(JsonElement option : options) {
                JsonObject optionObj = option.getAsJsonObject();
                if(!optionObj.get("title").getAsString().toLowerCase().contains("Correct".toLowerCase())) {
                    String optionId = optionObj.get("id").getAsString();
                    postIncorrectAnswer(testId, questionId, optionId);
                    break;
                }
            }
        }

        finishTest(testId);
    }
}