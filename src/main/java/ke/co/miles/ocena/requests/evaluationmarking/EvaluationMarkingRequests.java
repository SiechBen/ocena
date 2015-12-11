/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.evaluationmarking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.AssessedEvaluation;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.AssessedEvaluationCommentDetails;
import ke.co.miles.ocena.utilities.AssessedEvaluationDetails;
import ke.co.miles.ocena.utilities.CourseOfInstanceDetails;
import ke.co.miles.ocena.utilities.CourseOfSessionDetails;
import ke.co.miles.ocena.utilities.EvaluatedQuestionDetails;
import ke.co.miles.ocena.utilities.EvaluationInstanceDetails;
import ke.co.miles.ocena.utilities.EvaluationSessionDetails;
import ke.co.miles.ocena.utilities.MeansOfAnsweringDetail;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;
import ke.co.miles.ocena.utilities.RatingTypeDetail;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class EvaluationMarkingRequests extends EntityRequests implements EvaluationMarkingRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Mark evaluation">
    @Override
    public void markEvaluation(EvaluationSessionDetails evaluationSessionDetails) throws InvalidArgumentException {
        //Method for marking the evaluated question answers
        logger.log(Level.INFO, "Entered the method for marking the evaluated question answers");

        //Check validity of the evaluation session passed in
        logger.log(Level.INFO, "Checking validity of the evaluation session passed in");
        if (evaluationSessionDetails == null) {
            logger.log(Level.INFO, "The evaluation session is null");
            throw new InvalidArgumentException("error_030_01");
        } else if (evaluationSessionDetails.getId() == null) {
            logger.log(Level.INFO, "The evaluation session's unique identifier is null");
            throw new InvalidArgumentException("error_030_02");
        }

        //Retrieve the list of courses of this session
        logger.log(Level.INFO, "Retrieving the list of courses of this session");
        List<CourseOfSessionDetails> coursesOfSession = new ArrayList<>();
        try {
            coursesOfSession = courseOfSessionService.retrieveCoursesOfSession(evaluationSessionDetails);
        } catch (InvalidArgumentException | InvalidStateException e) {
            logger.log(Level.INFO, "An error occurred during ourses of session records retrieval");
        }

        //Retrieve the list of evaluation instances of this session
        logger.log(Level.INFO, "Retrieving the list of evaluation instances of this session");
        List<EvaluationInstanceDetails> evaluationInstances = new ArrayList<>();
        try {
            evaluationInstances = evaluationInstanceService.retrieveEvaluationInstances(evaluationSessionDetails);
        } catch (InvalidArgumentException | InvalidStateException e) {
            logger.log(Level.INFO, "An error occurred during evaluation instance records retrieval");
        }

        //Retrieve the list of evaluated questions for this session
        logger.log(Level.INFO, "Retrieving the list of evaluated questions for this session");
        List<EvaluatedQuestionDetails> evaluatedQuestions = new ArrayList<>();
        try {
            evaluatedQuestions = evaluatedQuestionService.retrieveEvaluatedQuestions(evaluationSessionDetails);
        } catch (InvalidArgumentException | InvalidStateException e) {
            logger.log(Level.INFO, "An error occurred during evaluated question records retrieval");
        }

        //Loop through all courses of session marking the evaluation for each
        logger.log(Level.INFO, "Looping through all courses of session marking the evaluation for each");
        for (CourseOfSessionDetails currentCourseOfSession : coursesOfSession) {

            //Mark the evaluation for the current course of session
            logger.log(Level.INFO, "Mark the evaluation for this course of session : {0}", currentCourseOfSession.getId());

            //Loop through all evaluated questions for this course of session assessing their evaluation
            logger.log(Level.INFO, "Looping through all evaluated questions for this course of session assessing their evaluation");
            for (EvaluatedQuestionDetails currentEvaluatedQuestion : evaluatedQuestions) {

                //Create an object to hold assessed evaluation details
                logger.log(Level.INFO, "Creating an object to hold assessed evaluation details");
                assessedEvaluationDetails = new AssessedEvaluationDetails();
                assessedEvaluationDetails.setActive(Boolean.TRUE);
                assessedEvaluationDetails.setCourseOfSession(currentCourseOfSession);
                assessedEvaluationDetails.setEvaluatedQuestion(currentEvaluatedQuestion);
                assessedEvaluationDetails.setEvaluationSession(evaluationSessionDetails);
                assessedEvaluationDetails.setQuestionDescription(currentEvaluatedQuestion.getQuestion());
                assessedEvaluationDetails.setQuestionCategory(currentEvaluatedQuestion.getQuestionCategory());

                //Record an assessed evaluation record in the database
                logger.log(Level.INFO, "Recording an assessed evaluation record in the database");
                try {
                    assessedEvaluationDetails = convertAssessedEvaluationToAssessedEvaluationDetails(assessedEvaluationService.addAssessedEvaluation(assessedEvaluationDetails));
                } catch (Exception e) {
                    logger.log(Level.INFO, "An error occurred while adding assessed evaluation record");
                }

                //<editor-fold defaultstate="collapsed" desc="By rating">
                if (currentEvaluatedQuestion.getMeansOfAnswering().equals(MeansOfAnsweringDetail.BY_RATING)) {

                    //The evaluated question is marked by rating
                    logger.log(Level.INFO, "The evaluated question is marked by rating");

                    if (currentEvaluatedQuestion.getRatingType().equals(RatingTypeDetail.STAR)) {

                        //The evaluated question is marked by star rating
                        logger.log(Level.INFO, "The evaluated question is marked by star rating");

                        //Declare and initialise variables to be used for marking star rating
                        logger.log(Level.INFO, "Declaring and initialising variables to be used for marking star rating");
                        double currentStarRating, totalStarRating = 0.0, averageStarRating = 0.0;
                        double sumOfSquaredDifferences = 0.0, meanOfSquaredDifferences, standardDeviation = 0.0;
                        ArrayList<Double> allStarRatings = new ArrayList<>();
                        int evaluationInstanceCount = 0;

                        //Loop through all evaluation instances for the current evaluated question
                        logger.log(Level.INFO, "Looping through all evaluation instances for the current evaluated question");
                        for (EvaluationInstanceDetails currentEvaluationInstance : evaluationInstances) {

                            //Retrieve the courses of this evaluation instance
                            logger.log(Level.INFO, "Retrieving the courses of this evaluation instance");
                            CourseOfInstanceDetails currentCourseOfInstance = new CourseOfInstanceDetails();
                            try {
                                currentCourseOfInstance = courseOfInstanceService.retrieveCourseOfInstance(currentEvaluationInstance, currentCourseOfSession);
                                if (currentCourseOfInstance == null) {
                                    continue;
                                }
                            } catch (InvalidArgumentException | InvalidStateException e) {
                                logger.log(Level.INFO, "An error occurred while retrieving courses of instance");
                            }

                            //Retrieve the evaluated question answer record for this evaluation instance
                            logger.log(Level.INFO, "Retrieving the evaluated question answer record for this evaluation instance");
                            try {

                                evaluatedQuestionAnswerDetails = evaluatedQuestionAnswerService.retrieveEvaluatedQuestionAnswer(currentEvaluationInstance, currentEvaluatedQuestion, currentCourseOfInstance);

                                //Break if evaluated question answer is null
                                if (evaluatedQuestionAnswerDetails == null) {
                                    logger.log(Level.INFO, "Break because evaluated question answer is null");
                                    break;
                                }

                            } catch (InvalidArgumentException | InvalidStateException e) {
                                logger.log(Level.INFO, "An error occurred during evaluated question answer record retrieval");
                            }

                            //Add the star rating from this evaluation instance to the sum of star rating for this evaluated question answer
                            logger.log(Level.INFO, "Adding the star rating from this evaluation instance to the sum of star rating for this evaluated question answer");

                            try {

                                currentStarRating = new Double(evaluatedQuestionAnswerDetails.getRating());

                                logger.log(Level.INFO, "The star rating is       : {0}", currentStarRating);

                                totalStarRating += currentStarRating;

                                allStarRatings.add(currentStarRating);

                                logger.log(Level.INFO, "The total rating is      : {0}", totalStarRating);

                                logger.log(Level.INFO, "Evluation instance count : {0}", ++evaluationInstanceCount);

                            } catch (NumberFormatException e) {
                                logger.log(Level.INFO, "The star rating is N/A");
                            } catch (NullPointerException ex) {
                                logger.log(Level.INFO, "The star rating is null");
                            } catch (Exception exception) {
                                logger.log(Level.SEVERE, "An error occurred during star rating summation");
                            }

                        }

                        //Calculate the average star rating
                        logger.log(Level.INFO, "Calculating the average and standard deviation of star ratings");
                        try {
                            if (evaluationInstanceCount != 0) {
                                averageStarRating = totalStarRating / evaluationInstanceCount;

                                //Get the sum of differences of average and star rating
                                for (double starRating : allStarRatings) {
                                    sumOfSquaredDifferences += Math.pow((starRating - averageStarRating), 2);
                                }

                                //Get the average of the differences
                                meanOfSquaredDifferences = sumOfSquaredDifferences / evaluationInstanceCount;

                                //Get the square root of the average found in the line above
                                //This is the standard deviation of the star ratings
                                standardDeviation = Math.pow(meanOfSquaredDifferences, 0.5);

                            }

                            logger.log(Level.INFO, "The average rating is        : {0}", averageStarRating);
                            logger.log(Level.INFO, "The standard deviation is        : {0}", standardDeviation);

                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "An error occurred during star rating average calculation");
                        }

                        //Record the average star rating to serve as the rating submitted for this evaluated question
                        //Record also the standard deviation for this result
                        logger.log(Level.INFO, "Recording the average star rating to serve as the rating submitted for this evaluated question \n"
                                + "\t\tRecord also the standard deviation for this result");
                        assessedEvaluationDetails.setRating(String.valueOf(averageStarRating));
                        assessedEvaluationDetails.setStandardDeviation(standardDeviation);

                    } else if (currentEvaluatedQuestion.getRatingType().equals(RatingTypeDetail.BOOLEAN)) {

                        //The evaluated question is marked by boolean rating
                        logger.log(Level.INFO, "The evaluated question is marked by boolean rating");

                        //Declare and initialise variables to be used for marking boolean rating
                        logger.log(Level.INFO, "Declaring and initialising variables to be used for marking boolean rating");
                        boolean currentBooleanRating;
                        int totalTrueCount = 0, totalFalseCount = 0, evaluationInstanceCount = 0;
                        double trueCountPercentage = 0.0;

                        //Loop through all evaluation instances for the current evaluated question
                        logger.log(Level.INFO, "Looping through all evaluation instances for the current evaluated question");
                        for (EvaluationInstanceDetails currentEvaluationInstance : evaluationInstances) {

                            //Retrieve the courses of this evaluation instance
                            logger.log(Level.INFO, "Retrieving the courses of this evaluation instance");
                            CourseOfInstanceDetails currentCourseOfInstance = new CourseOfInstanceDetails();
                            try {
                                currentCourseOfInstance = courseOfInstanceService.retrieveCourseOfInstance(currentEvaluationInstance, currentCourseOfSession);
                                if (currentCourseOfInstance == null) {
                                    continue;
                                }
                            } catch (InvalidArgumentException | InvalidStateException e) {
                                logger.log(Level.INFO, "An error occurred while retrieving courses of instance");
                            }

                            //Retrieve the evaluated question answer record for this evaluation instance
                            logger.log(Level.INFO, "Retrieving the evaluated question answer record for this evaluation instance");
                            try {

                                evaluatedQuestionAnswerDetails = evaluatedQuestionAnswerService.retrieveEvaluatedQuestionAnswer(currentEvaluationInstance, currentEvaluatedQuestion, currentCourseOfInstance);

                                //Break if evaluated question answer is null
                                if (evaluatedQuestionAnswerDetails == null) {
                                    logger.log(Level.INFO, "Break because evaluated question answer is null");
                                    break;
                                }

                            } catch (InvalidArgumentException | InvalidStateException e) {
                                logger.log(Level.INFO, "An error occurred during evaluated question answer record retrieval");
                            }

                            //Add the boolean rating from this evaluation instance to the sum of corresponding boolean rating for this evaluated question answer
                            logger.log(Level.INFO, "Adding the boolean rating from this evaluation instance to the sum of corresponding boolean rating for this evaluated question answer");

                            try {

                                currentBooleanRating = Boolean.valueOf(evaluatedQuestionAnswerDetails.getRating());

                                logger.log(Level.INFO, "The boolean rating is        : {0}", currentBooleanRating);

                                if (currentBooleanRating == true) {

                                    logger.log(Level.INFO, "The total true count is  : {0}", ++totalTrueCount);

                                } else if (currentBooleanRating == false) {

                                    logger.log(Level.INFO, "The total false count is : {0}", ++totalFalseCount);

                                }

                                logger.log(Level.INFO, "Evluation instance count     : {0}", ++evaluationInstanceCount);

                            } catch (NumberFormatException e) {
                                logger.log(Level.INFO, "The boolean rating is N/A");
                            } catch (NullPointerException ex) {
                                logger.log(Level.INFO, "The boolean rating is null");
                            } catch (Exception exception) {
                                logger.log(Level.SEVERE, "An error occurred during boolean rating summation");
                            }

                        }

                        //Calculate appearance of true by percentage
                        logger.log(Level.INFO, "Calculating appearance of true by percentage");
                        try {

                            if (evaluationInstanceCount != 0) {
                                trueCountPercentage = totalTrueCount / evaluationInstanceCount * 100;
                            }

                            logger.log(Level.INFO, "The true count percentage is     : {0}", trueCountPercentage);

                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "An error occurred during boolean rating average calculation");
                        }

                        //Record the average boolean rating to serve as the rating submitted for this evaluated question
                        logger.log(Level.INFO, "Recording the average boolean rating to serve as the rating submitted for this evaluated question");
                        assessedEvaluationDetails.setPercentageScore(String.valueOf(trueCountPercentage) + "%");

                    } else if (currentEvaluatedQuestion.getRatingType().equals(RatingTypeDetail.YES_OR_NO)) {

                        //The evaluated question is marked by yes or no rating
                        logger.log(Level.INFO, "The evaluated question is marked by yes or no rating");

                        //Declare and initialise variables to be used for marking yes or no rating
                        logger.log(Level.INFO, "Declaring and initialising variables to be used for marking yes or no rating");
                        String currentYesOrNoRating;
                        int totalYesCount = 0, totalNoCount = 0, evaluationInstanceCount = 0;
                        double yesCountPercentage = 0.0;

                        //Loop through all evaluation instances for the current evaluated question
                        logger.log(Level.INFO, "Looping through all evaluation instances for the current evaluated question");
                        for (EvaluationInstanceDetails currentEvaluationInstance : evaluationInstances) {

                            //Retrieve the courses of this evaluation instance
                            logger.log(Level.INFO, "Retrieving the courses of this evaluation instance");
                            CourseOfInstanceDetails currentCourseOfInstance = new CourseOfInstanceDetails();
                            try {
                                currentCourseOfInstance = courseOfInstanceService.retrieveCourseOfInstance(currentEvaluationInstance, currentCourseOfSession);
                                if (currentCourseOfInstance == null) {
                                    continue;
                                }
                            } catch (InvalidArgumentException | InvalidStateException e) {
                                logger.log(Level.INFO, "An error occurred while retrieving courses of instance");
                            }

                            //Retrieve the evaluated question answer record for this evaluation instance
                            logger.log(Level.INFO, "Retrieving the evaluated question answer record for this evaluation instance");
                            try {

                                evaluatedQuestionAnswerDetails = evaluatedQuestionAnswerService.retrieveEvaluatedQuestionAnswer(currentEvaluationInstance, currentEvaluatedQuestion, currentCourseOfInstance);

                                //Break if evaluated question answer is null
                                if (evaluatedQuestionAnswerDetails == null) {
                                    logger.log(Level.INFO, "Break because evaluated question answer is null");
                                    break;
                                }

                            } catch (InvalidArgumentException | InvalidStateException e) {
                                logger.log(Level.INFO, "An error occurred during evaluated question answer record retrieval");
                            }

                            //Add the yes or no rating from this evaluation instance to the sum of corresponding yes or no rating for this evaluated question answer
                            logger.log(Level.INFO, "Adding the yes or no rating from this evaluation instance to the sum of corresponding yes or no rating for this evaluated question answer");

                            try {

                                currentYesOrNoRating = evaluatedQuestionAnswerDetails.getRating();

                                logger.log(Level.INFO, "The yes or no rating is          : {0}", currentYesOrNoRating);

                                switch (currentYesOrNoRating.toUpperCase()) {
                                    case "YES":

                                        logger.log(Level.INFO, "The total yes count is   : {0}", ++totalYesCount);

                                        logger.log(Level.INFO, "Evluation instance count : {0}", ++evaluationInstanceCount);

                                        break;

                                    case "NO":

                                        logger.log(Level.INFO, "The total no count is    : {0}", ++totalNoCount);

                                        logger.log(Level.INFO, "Evluation instance count : {0}", ++evaluationInstanceCount);

                                        break;

                                    default:
                                        break;
                                }

                            } catch (NumberFormatException e) {
                                logger.log(Level.INFO, "The yes or no rating is N/A");
                            } catch (NullPointerException ex) {
                                logger.log(Level.INFO, "The yes or no rating is null");
                            } catch (Exception exception) {
                                logger.log(Level.SEVERE, "An error occurred during yes or no rating summation");
                            }

                        }

                        //Calculate appearance of true by percentage
                        logger.log(Level.INFO, "Calculating appearance of true by percentage");
                        try {

                            if (evaluationInstanceCount != 0) {
                                yesCountPercentage = totalYesCount / evaluationInstanceCount * 100;
                            }

                            logger.log(Level.INFO, "The yes count percentage is    : {0}", yesCountPercentage);

                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "An error occurred during yes or no rating average calculation");
                        }

                        //Record the average yes or no rating to serve as the rating submitted for this evaluated question
                        logger.log(Level.INFO, "Recording the average yes or no rating to serve as the rating submitted for this evaluated question");
                        assessedEvaluationDetails.setPercentageScore(String.valueOf(yesCountPercentage) + "%");

                    } else if (currentEvaluatedQuestion.getRatingType().equals(RatingTypeDetail.PERCENTAGE)) {

                        //The evaluated question is marked by percentage rating
                        logger.log(Level.INFO, "The evaluated question is marked by percentage rating");

                        //Declare and initialise variables to be used for marking percentage rating
                        logger.log(Level.INFO, "Declaring and initialising variables to be used for marking percentage rating");
                        String percentageRatingString;
                        String[] percentageRangeTerminalValues;
                        double startOfPercentageRange, endOfPercentageRange;
                        double currentPercentageRating = 0.0, totalPercentageRating = 0.0, averagePercentageRating = 0.0;
                        int evaluationInstanceCount = 0;

                        //Loop through all evaluation instances for the current evaluated question
                        logger.log(Level.INFO, "Looping through all evaluation instances for the current evaluated question");
                        for (EvaluationInstanceDetails currentEvaluationInstance : evaluationInstances) {

                            //Retrieve the courses of this evaluation instance
                            logger.log(Level.INFO, "Retrieving the courses of this evaluation instance");
                            CourseOfInstanceDetails currentCourseOfInstance = new CourseOfInstanceDetails();
                            try {
                                currentCourseOfInstance = courseOfInstanceService.retrieveCourseOfInstance(currentEvaluationInstance, currentCourseOfSession);
                                if (currentCourseOfInstance == null) {
                                    continue;
                                }
                            } catch (InvalidArgumentException | InvalidStateException e) {
                                logger.log(Level.INFO, "An error occurred while retrieving courses of instance");
                            }

                            //Retrieve the evaluated question answer record for this evaluation instance
                            logger.log(Level.INFO, "Retrieving the evaluated question answer record for this evaluation instance");
                            try {

                                evaluatedQuestionAnswerDetails = evaluatedQuestionAnswerService.retrieveEvaluatedQuestionAnswer(currentEvaluationInstance, currentEvaluatedQuestion, currentCourseOfInstance);

                                //Break if evaluated question answer is null
                                if (evaluatedQuestionAnswerDetails == null) {
                                    logger.log(Level.INFO, "Break because evaluated question answer is null");
                                    break;
                                }

                            } catch (InvalidArgumentException | InvalidStateException e) {
                                logger.log(Level.INFO, "An error occurred during evaluated question answer record retrieval");
                            }

                            //Add the percentage rating from this evaluation instance to the sum of percentage rating for this evaluated question answer
                            logger.log(Level.INFO, "Adding the percentage rating from this evaluation instance to the sum of percentage rating for this evaluated question answer");

                            try {

                                //Get rating string and remove the '%' character and all spaces
                                logger.log(Level.INFO, "Getting the rating string and removing the '%' character and all spaces");

                                percentageRatingString = evaluatedQuestionAnswerDetails.getRating();

                                percentageRatingString = percentageRatingString.replace('%', '\0').trim();
                                percentageRatingString = percentageRatingString.replaceAll(" ", "").trim();

                                if (percentageRatingString.contains("-")) {

                                    //Percentage rating appears over a range
                                    logger.log(Level.INFO, "Percentage rating appears over a range");

                                    //Get the range terminal values
                                    logger.log(Level.INFO, "Getting the range terminal values");

                                    percentageRangeTerminalValues = percentageRatingString.split("-");

                                    startOfPercentageRange = new Double(percentageRangeTerminalValues[0]);
                                    endOfPercentageRange = new Double(percentageRangeTerminalValues[1]);

                                    //Get the midway value
                                    logger.log(Level.INFO, "Getting the midway value");
                                    currentPercentageRating = (endOfPercentageRange + startOfPercentageRange) / 2;

                                } else if (percentageRatingString.length() <= 3) {

                                    //Percentage rating is a direct value
                                    logger.log(Level.INFO, "Percentage rating is a direct value");

                                    //Get the value
                                    logger.log(Level.INFO, "Getting the value");
                                    currentPercentageRating = new Double(percentageRatingString);

                                } else {

                                    if (percentageRatingString.toLowerCase().contains("below")) {

                                        //Percentage rating has the keyword 'below'
                                        logger.log(Level.INFO, "Percentage rating has the keyword 'below'");

                                        //Get the midway value between 0 and the value
                                        logger.log(Level.INFO, "Getting the midway value between 0 and the value");
                                        try {
                                            currentPercentageRating = new Double(percentageRatingString.toLowerCase().replace("below", "")) / 2;
                                        } catch (NumberFormatException e) {
                                            logger.log(Level.INFO, "Rating value is malformed");
                                        }
                                    } else if (percentageRatingString.toLowerCase().contains("above")) {

                                        //Percentage rating has the keyword 'above'
                                        logger.log(Level.INFO, "Percentage rating has the keyword 'above'");

                                        //Get the midway value between the value and 100
                                        logger.log(Level.INFO, "Getting the midway value between the value and 100");
                                        try {
                                            currentPercentageRating = (100 + new Double(percentageRatingString.toLowerCase().replace("above", ""))) / 2;
                                        } catch (NumberFormatException e) {
                                            logger.log(Level.INFO, "Rating value is malformed");
                                        }
                                    }
                                }

                                logger.log(Level.INFO, "The percentage rating is : {0}", currentPercentageRating);

                                totalPercentageRating += currentPercentageRating;

                                logger.log(Level.INFO, "The total rating is      : {0}", totalPercentageRating);

                                logger.log(Level.INFO, "Evluation instance count : {0}", ++evaluationInstanceCount);

                            } catch (NumberFormatException e) {
                                logger.log(Level.INFO, "The percentage rating is N/A");
                            } catch (NullPointerException ex) {
                                logger.log(Level.INFO, "The percentage rating is null");
                            } catch (Exception exc) {
                                logger.log(Level.SEVERE, "An error occurred during percentage rating summation");
                            }

                        }

                        //Calculate the average percentage rating
                        logger.log(Level.INFO, "Calculating the average percentage rating");
                        try {
                            if (evaluationInstanceCount != 0) {
                                averagePercentageRating = totalPercentageRating / evaluationInstanceCount;
                            }

                            logger.log(Level.INFO, "The average rating is        : {0}", averagePercentageRating);

                        } catch (Exception e) {
                            logger.log(Level.SEVERE, "An error occurred during percentage rating average calculation");
                        }

                        //Record the average percentage rating to serve as the rating submitted for this evaluated question
                        logger.log(Level.INFO, "Recording the average percentage rating to serve as the rating submitted for this evaluated question");
                        assessedEvaluationDetails.setPercentageScore(averagePercentageRating + "%");

                    }

                    //Edit the assessed evaluation record in the database
                    logger.log(Level.INFO, "Editing the assessed evaluation record in the database");
                    try {
                        assessedEvaluationService.editAssessedEvaluation(assessedEvaluationDetails);
                    } catch (InvalidArgumentException | InvalidStateException e) {
                        logger.log(Level.INFO, "An error occurred while adding assessed evaluation record");
                    }

                    //</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="By reasoning">
                } else if (currentEvaluatedQuestion.getMeansOfAnswering().equals(MeansOfAnsweringDetail.BY_REASONING)) {

                    //The evaluated question is answered by reasoning
                    logger.log(Level.INFO, "The evaluated question is answered by reasoning");

                    //Declare and initialise variables to be used for assessing the reasons
                    logger.log(Level.INFO, "Declaring and initialising variables to be used for assessing the reasons");
                    Integer[] sizeArray;
                    Object[] allReasonsArray;
                    Object[] similarReasonsArray;
                    int evaluationInstanceCount = 0;
                    List<Integer> sizeOfList = new ArrayList<>();
                    List<String> allReasonsList = new ArrayList<>();
                    List<String> similarReasonsPairs = new ArrayList<>();
                    List<String> similarReasonsList = new ArrayList<>();
                    List<String> assessedReasonsList = new ArrayList<>();
                    Map<Integer, List<String>> similarReasonsMap = new HashMap<>();
                    Map<Integer, List<String>> similarReasonsHold;

                    //Loop through all evaluation instances for the current evaluated question
                    logger.log(Level.INFO, "Looping through all evaluation instances for the current evaluated question");
                    for (EvaluationInstanceDetails currentEvaluationInstance : evaluationInstances) {

                        //Retrieve the courses of this evaluation instance
                        logger.log(Level.INFO, "Retrieving the courses of this evaluation instance");
                        CourseOfInstanceDetails currentCourseOfInstance = new CourseOfInstanceDetails();
                        try {
                            currentCourseOfInstance = courseOfInstanceService.retrieveCourseOfInstance(currentEvaluationInstance, currentCourseOfSession);
                            if (currentCourseOfInstance == null) {
                                continue;
                            }
                        } catch (InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "An error occurred while retrieving courses of instance");
                        }

                        //Retrieve the evaluated question answer record for this evaluation instance
                        logger.log(Level.INFO, "Retrieving the evaluated question answer record for this evaluation instance");
                        try {

                            evaluatedQuestionAnswerDetails = evaluatedQuestionAnswerService.retrieveEvaluatedQuestionAnswer(currentEvaluationInstance, currentEvaluatedQuestion, currentCourseOfInstance);

                            //Break if evaluated question answer is null
                            if (evaluatedQuestionAnswerDetails == null) {
                                logger.log(Level.INFO, "Break because evaluated question answer is null");
                                continue;
                            }

                        } catch (InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "An error occurred during evaluated question answer record retrieval");
                        }

                        //Add the evaluated question reasoning to the list of this evaluated question's reasoning
                        logger.log(Level.INFO, "Add the evaluated question reasoning to the list of this evaluated question's reasoning");
                        try {

                            allReasonsList.add(evaluatedQuestionAnswerDetails.getReasoning());

                            logger.log(Level.INFO, "Reasoning is added to the list successfully");

                        } catch (Exception e) {
                            logger.log(Level.INFO, "An error occurred while adding the reasoning to the list");
                        }

                        logger.log(Level.INFO, "Evluation instance count     : {0}", ++evaluationInstanceCount);

                    }

                    //Assess the reasonings by retaining an instance of the repeated reasons and removing the rest
                    logger.log(Level.INFO, "Assessing the reasonings by retaining an instance of the repeated reasons and removing the rest");

                    //Put the items of the list with all reasons in an array
                    logger.log(Level.INFO, "Putting the items of the list with all reasons in an array");
                    allReasonsArray = allReasonsList.toArray();

                    for (Integer i = 0; i < allReasonsArray.length - 1; i++) {
                        for (Integer j = i; j < allReasonsArray.length; j++) {

                            if (similar((String) allReasonsArray[i], (String) allReasonsArray[j])) {

                                //Add the reasons in a list with pairs of similar reasons because they are similar
                                logger.log(Level.INFO, "Adding the reasons in a list with pairs of similar reasons because they are similar");
                                similarReasonsPairs.add((String) allReasonsArray[i]);
                                similarReasonsPairs.add((String) allReasonsArray[j]);

                                //Remove the reasons from the list containing all reasons
                                logger.log(Level.INFO, "Removing the reasons from the list containing all reasons");
                                allReasonsList.remove((String) allReasonsArray[i]);
                                allReasonsList.remove((String) allReasonsArray[j]);

                            } else {

                                //Remove the reasons from the list containing all reasons
                                logger.log(Level.INFO, "Removing the reasons from the list containing all reasons");
                                allReasonsList.remove((String) allReasonsArray[i]);
                                allReasonsList.remove((String) allReasonsArray[j]);

                            }
                        }
                    }

                    //Put the items of the list with pairs of similar reasons in an array
                    logger.log(Level.INFO, "Putting the items of the list with pairs of similar  reasons in an array");
                    similarReasonsArray = similarReasonsPairs.toArray();

                    //Put the similar reasons in a map of an identifying number and the list of similar reasons
                    logger.log(Level.INFO, "Putting the similar reasons in a map of an identifying number and the list of similar reasons");
                    for (Integer i = 0; i < similarReasonsArray.length - 1; i++) {
                        for (Integer j = i; j < similarReasonsArray.length; j++) {

                            if (similar((String) similarReasonsArray[i], (String) similarReasonsArray[j])) {

                                for (Integer key : similarReasonsMap.keySet()) {
                                    if (!similarReasonsMap.get(key).contains((String) similarReasonsArray[i])) {

                                        logger.log(Level.INFO, "The reason is not in the list yet hence add to list");
                                        similarReasonsList.add((String) similarReasonsArray[i]);

                                        logger.log(Level.INFO, "Remove the reason from the list of similar pairs of reasons");
                                        similarReasonsPairs.remove((String) similarReasonsArray[i]);

                                    }

                                    if (!similarReasonsMap.get(key).contains((String) similarReasonsArray[j])) {

                                        logger.log(Level.INFO, "The reason is not in the list yet hence add to list");
                                        similarReasonsList.add((String) similarReasonsArray[j]);

                                        logger.log(Level.INFO, "Remove the reason from the list of similar pairs of reasons");
                                        similarReasonsPairs.remove((String) similarReasonsArray[j]);

                                    }
                                }
                            }
                        }

                        logger.log(Level.INFO, "Put the list of similar comments in the map of similar reasons");
                        similarReasonsMap.put(i, similarReasonsList);

                    }

                    if (similarReasonsMap.size() > 5) {

                        //Strive to retain only five reasons
                        logger.log(Level.INFO, "Striving to retain only five reasons");

                        //Put the sizes of the lists in the map in a list
                        logger.log(Level.INFO, "Put the sizes of the lists in the map in a list");
                        for (Integer key : similarReasonsMap.keySet()) {

                            sizeOfList.add(similarReasonsMap.get(key).size());

                        }

                        //Create an array out of the list of sizes
                        logger.log(Level.INFO, "Creating an array out of the list of sizes");
                        sizeArray = sizeOfList.toArray(new Integer[sizeOfList.size()]);

                        //Sort the array of sizes in descending order
                        logger.log(Level.INFO, "Sorting the array of sizes in descending order");
                        for (Integer i = 1; i < sizeArray.length; i++) {
                            for (Integer j = 0; j < sizeArray.length - 1; j++) {
                                if (sizeArray[j] < sizeArray[j + 1]) {

                                    int hold = sizeArray[j];
                                    sizeArray[j] = sizeArray[j + 1];
                                    sizeArray[j + 1] = hold;

                                }
                            }
                        }

                        //Retain the top 5 longest list of reasons
                        logger.log(Level.INFO, "Retain the top 5 longest list of reasons");
                        //Iterator<Integer> key = Arrays.asList(similarCommentsHold.keySet().toArray(new Integer[similarCommentsHold.size()])).listIterator();
                        similarReasonsHold = similarReasonsMap;
                        //while(key.hasNext()){
                        for (Integer key : similarReasonsHold.keySet()) {

                            boolean retain = false;
                            for (Integer i = 0; i < 5; i++) {
                                if (similarReasonsHold.get(key).size() == sizeArray[i]) {
                                    retain = true;
                                    break;
                                }
                            }

                            if (!retain) {

                                //Remove the shorter list
                                logger.log(Level.INFO, "Removing the shorter list");
                                similarReasonsMap.remove(key);

                            }
                        }
                    }
                    //}

                    //Extract only one reason from the list of similar reasons. This is the assessed reason
                    logger.log(Level.INFO, "Extract only one reason from the list of similar reasons. This is the assessed reason");
                    for (Integer key : similarReasonsMap.keySet()) {
                        for (String reason : similarReasonsMap.get(key)) {
                            if (reason != null && reason.trim().length() > 0) {
                                assessedReasonsList.add(reason);
                                break;
                            }
                        }
                    }

                    //Record the assessed reasons in the database
                    logger.log(Level.INFO, "Recording the assessed reasons in the database");
                    for (String assessedReason : assessedReasonsList) {

                        assessedEvaluationCommentDetails = new AssessedEvaluationCommentDetails();
                        assessedEvaluationCommentDetails.setActive(Boolean.TRUE);
                        assessedEvaluationCommentDetails.setComment(assessedReason);
                        assessedEvaluationCommentDetails.setAssessedEvaluation(assessedEvaluationDetails);

                        try {

                            assessedEvaluationCommentService.addAssessedEvaluationComment(assessedEvaluationCommentDetails);

                        } catch (Exception e) {
                            logger.log(Level.INFO, "An error occurred while adding the assessed reason to the database");
                        }

                        break;

                    }

//</editor-fold>
                    //<editor-fold defaultstate="collapsed" desc="By listing comments">
                } else if (currentEvaluatedQuestion.getMeansOfAnswering().equals(MeansOfAnsweringDetail.BY_LISTING_COMMENTS)) {

                    //The evaluated question is answered by commenting
                    logger.log(Level.INFO, "The evaluated question is answered by commenting");

                    //Declare and initialise variables to be used for assessing the comments
                    logger.log(Level.INFO, "Declaring and initialising variables to be used for assessing the comments");
                    Integer[] sizeArray;
                    Object[] allCommentsArray;
                    Object[] similarCommentsArray;
                    int evaluationInstanceCount = 0;
                    List<Integer> sizeOfList = new ArrayList<>();
                    List<String> allCommentsList = new ArrayList<>();
                    List<String> similarCommentsPairs = new ArrayList<>();
                    List<String> similarCommentsList = new ArrayList<>();
                    List<String> assessedCommentsList = new ArrayList<>();
                    Map<Integer, List<String>> similarCommentsMap = new HashMap<>();
                    Map<Integer, List<String>> similarCommentsHold;

                    //Loop through all evaluation instances for the current evaluated question
                    logger.log(Level.INFO, "Looping through all evaluation instances for the current evaluated question");
                    for (EvaluationInstanceDetails currentEvaluationInstance : evaluationInstances) {

                        //Retrieve the courses of this evaluation instance
                        logger.log(Level.INFO, "Retrieving the courses of this evaluation instance");
                        CourseOfInstanceDetails currentCourseOfInstance = new CourseOfInstanceDetails();
                        try {
                            currentCourseOfInstance = courseOfInstanceService.retrieveCourseOfInstance(currentEvaluationInstance, currentCourseOfSession);
                            if (currentCourseOfInstance == null) {
                                continue;
                            }
                        } catch (InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "An error occurred while retrieving courses of instance");
                        }

                        //Retrieve the evaluated question answer record for this evaluation instance
                        logger.log(Level.INFO, "Retrieving the evaluated question answer record for this evaluation instance");
                        try {

                            evaluatedQuestionAnswerDetails = evaluatedQuestionAnswerService.retrieveEvaluatedQuestionAnswer(currentEvaluationInstance, currentEvaluatedQuestion, currentCourseOfInstance);

                            //Break if evaluated question answer is null
                            if (evaluatedQuestionAnswerDetails == null) {
                                logger.log(Level.INFO, "Break because evaluated question answer is null");
                                break;
                            }

                        } catch (InvalidArgumentException | InvalidStateException e) {
                            logger.log(Level.INFO, "An error occurred during evaluated question answer record retrieval");
                        }

                        //Add the evaluated question comments to the list of this evaluated question's comments
                        logger.log(Level.INFO, "Add the evaluated question comments to the list of this evaluated question's comments");
                        try {

                            allCommentsList.add(evaluatedQuestionAnswerDetails.getComment1());
                            allCommentsList.add(evaluatedQuestionAnswerDetails.getComment2());
                            allCommentsList.add(evaluatedQuestionAnswerDetails.getComment3());

                            logger.log(Level.INFO, "Comments are added to the list successfully");

                        } catch (Exception e) {
                            logger.log(Level.INFO, "An error occurred while adding the comments to the list");
                        }

                        logger.log(Level.INFO, "Evluation instance count     : {0}", ++evaluationInstanceCount);

                    }

                    //Assess the comments by retaining an instance of the repeated comments and removing the rest
                    logger.log(Level.INFO, "Assessing the comments by retaining an instance of the repeated comments and removing the rest");

                    //Put the items of the list with all comments in an array
                    logger.log(Level.INFO, "Putting the items of the list with all comments in an array");
                    allCommentsArray = allCommentsList.toArray();

                    for (Integer i = 0; i < allCommentsArray.length - 1; i++) {
                        for (Integer j = i; j < allCommentsArray.length; j++) {

                            if (similar((String) allCommentsArray[i], (String) allCommentsArray[j])) {

                                //Add the comments in a list with pairs of similar comments because they are similar
                                logger.log(Level.INFO, "Adding the comments in a list with pairs of similar comments because they are similar");
                                similarCommentsPairs.add((String) allCommentsArray[i]);
                                similarCommentsPairs.add((String) allCommentsArray[j]);

                                //Remove the comments from the list containing all comments
                                logger.log(Level.INFO, "Removing the comments from the list containing all comments");
                                allCommentsList.remove((String) allCommentsArray[i]);
                                allCommentsList.remove((String) allCommentsArray[j]);

                            } else {

                                //Remove the comments from the list containing all comments
                                logger.log(Level.INFO, "Removing the comments from the list containing all comments");
                                allCommentsList.remove((String) allCommentsArray[i]);
                                allCommentsList.remove((String) allCommentsArray[j]);

                            }
                        }
                    }

                    //Put the items of the list with pairs of similar comments in an array
                    logger.log(Level.INFO, "Putting the items of the list with pairs of similar  comments in an array");
                    similarCommentsArray = similarCommentsPairs.toArray();

                    //Put the similar comments in a map of an identifying number and the list of similar comments
                    logger.log(Level.INFO, "Putting the similar comments in a map of an identifying number and the list of similar comments");
                    for (Integer i = 0; i < similarCommentsArray.length - 1; i++) {
                        for (Integer j = i; j < similarCommentsArray.length; j++) {

                            if (similar((String) similarCommentsArray[i], (String) similarCommentsArray[j])) {

                                for (Integer key : similarCommentsMap.keySet()) {
                                    if (!similarCommentsMap.get(key).contains((String) similarCommentsArray[i])) {

                                        logger.log(Level.INFO, "The comment is not in the list yet hence add to list");
                                        similarCommentsList.add((String) similarCommentsArray[i]);

                                        logger.log(Level.INFO, "Remove the comment from the list of similar pairs of comments");
                                        similarCommentsPairs.remove((String) similarCommentsArray[i]);

                                    }

                                    if (!similarCommentsMap.get(key).contains((String) similarCommentsArray[j])) {

                                        logger.log(Level.INFO, "The comment is not in the list yet hence add to list");
                                        similarCommentsList.add((String) similarCommentsArray[j]);

                                        logger.log(Level.INFO, "Remove the comment from the list of similar pairs of comments");
                                        similarCommentsPairs.remove((String) similarCommentsArray[j]);

                                    }
                                }
                            }
                        }

                        logger.log(Level.INFO, "Put the list of similar comments in the map of similar comments");
                        similarCommentsMap.put(i, similarCommentsList);

                    }

                    if (similarCommentsMap.size() > 5) {

                        //Strive to retain only five comments
                        logger.log(Level.INFO, "Striving to retain only five comments");

                        //Put the sizes of the lists in the map in a list
                        logger.log(Level.INFO, "Put the sizes of the lists in the map in a list");
                        for (Integer key : similarCommentsMap.keySet()) {

                            sizeOfList.add(similarCommentsMap.get(key).size());

                        }

                        //Create an array out of the list of sizes
                        logger.log(Level.INFO, "Creating an array out of the list of sizes");
                        sizeArray = sizeOfList.toArray(new Integer[sizeOfList.size()]);

                        //Sort the array of sizes in descending order
                        logger.log(Level.INFO, "Sorting the array of sizes in descending order");
                        for (Integer i = 1; i < sizeArray.length; i++) {
                            for (Integer j = 0; j < sizeArray.length - 1; j++) {
                                if (sizeArray[j] < sizeArray[j + 1]) {

                                    int hold = sizeArray[j];
                                    sizeArray[j] = sizeArray[j + 1];
                                    sizeArray[j + 1] = hold;

                                }
                            }
                        }

                        //Retain the top 5 longest list of comments
                        logger.log(Level.INFO, "Retain the top 5 longest list of comments");
                        similarCommentsHold = similarCommentsMap;
                        //Iterator<Integer> key = Arrays.asList(similarCommentsHold.keySet().toArray(new Integer[similarCommentsHold.size()])).listIterator();
                        //while(key.hasNext()){
                        for (Integer key : similarCommentsHold.keySet()) {

                            boolean retain = false;
                            for (Integer i = 0; i < 5; i++) {
                                if (similarCommentsHold.get(key).size() == sizeArray[i]) {
                                    retain = true;
                                    break;
                                }
                            }

                            if (!retain) {

                                //Remove the shorter list
                                logger.log(Level.INFO, "Removing the shorter list");
                                similarCommentsMap.remove(key);

                            }
                        }
                    }
                    //}

                    //Extract only one comment from the list of similar comments. This is the assessed comment
                    logger.log(Level.INFO, "Extract only one comment from the list of similar comments. This is the assessed comment");
                    for (Integer key : similarCommentsMap.keySet()) {
                        for (String comment : similarCommentsMap.get(key)) {
                            if (comment != null && comment.trim().length() > 0) {
                                assessedCommentsList.add(comment);
                                break;
                            }
                        }
                    }

                    //Record the assessed comments in the database
                    logger.log(Level.INFO, "Recording the assessed comments in the database");
                    for (String assessedComment : assessedCommentsList) {

                        assessedEvaluationCommentDetails = new AssessedEvaluationCommentDetails();
                        assessedEvaluationCommentDetails.setActive(Boolean.TRUE);
                        assessedEvaluationCommentDetails.setComment(assessedComment);
                        assessedEvaluationCommentDetails.setAssessedEvaluation(assessedEvaluationDetails);

                        try {

                            assessedEvaluationCommentService.addAssessedEvaluationComment(assessedEvaluationCommentDetails);

                        } catch (Exception e) {
                            logger.log(Level.INFO, "An error occurred while adding the assessed comment to the database");
                        }
                        break;
                    }

                    //</editor-fold>
                }

            }

        }

    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Evaluate similarity">

    private boolean similar(String oneString, String theOtherString) {
        //Method for determining similar comments/reasons
        logger.log(Level.INFO, "Entererd method for determining similar strings");

        //Declare the variables to be used in determining similar comments/reasons
        logger.log(Level.INFO, "Declaring the variables to be used in determining similar comments/reasons");
        logger.log(Level.INFO, "\nOne string is      : {0}", oneString);
        logger.log(Level.INFO, "The other string is: {0}\n", theOtherString);

        if ((oneString == null || oneString.trim().length() == 0) && (theOtherString == null || theOtherString.trim().length() == 0)) {
            logger.log(Level.INFO, "Both strings are null, returning false");
            return false;
        } else if (oneString == null || oneString.trim().length() == 0) {
            logger.log(Level.INFO, "The first string is null, returning false");
            return false;
        } else if (theOtherString == null || theOtherString.trim().length() == 0) {
            logger.log(Level.INFO, "The second string is null, returning false");
            return false;

        }

        //Declare the variables to be used in determining similar comments/reasons
        logger.log(Level.INFO, "Declaring the variables to be used in determining similar comments/reasons");
        int orderedWordsCount;
        int shorterStringLength;
        String[] oneStringArray, theOtherStringArray;
        Integer indexOfSubstring = 0, indexOfOtherSubstring = 0;
        Map<Integer, String> sameWordsInOneString = new HashMap<>();
        Integer[] indexArrayOfSameInOne, indexArrayOfSameInTheOther;
        Map<Integer, String> sameWordsInTheOtherString = new HashMap<>();

        //Split the two strings into an array of words
        logger.log(Level.INFO, "Splitting the two strings into an array of words");
        oneString = oneString.toLowerCase();
        oneStringArray = oneString.split(" ");
        theOtherString = theOtherString.toLowerCase();
        theOtherStringArray = theOtherString.split(" ");

        //<editor-fold defaultstate="collapsed" desc="Negation checks">
        //Check if the strings have negations
        logger.log(Level.INFO, "Checking if the strings have negations");
        if ((oneString.contains("not") && !theOtherString.contains("not")) || (!oneString.contains("not") && theOtherString.contains("not"))) {

            logger.log(Level.INFO, "The two strings are not similar, returning false");
            return false;

        } else if ((oneString.contains("n't") && !theOtherString.contains("n't")) || (!oneString.contains("n't") && theOtherString.contains("n't"))) {

            logger.log(Level.INFO, "The two strings are not similar, returning false");
            return false;

        } else if ((oneString.contains("not") && theOtherString.contains("not"))) {

            //Both strings contain negation word 'not'
            logger.log(Level.INFO, "Both strings contain negation word 'not'");

            //Note the index of the word
            logger.log(Level.INFO, "Noting the index of the word");

            for (Integer i = 0; i < oneStringArray.length; i++) {
                if (oneStringArray[i].equals("not")) {
                    indexOfSubstring = i;
                    break;
                }
            }

            for (Integer i = 0; i < oneStringArray.length; i++) {
                if (oneStringArray[i].equals("not")) {
                    indexOfOtherSubstring = i;
                    break;
                }
            }

            try {

                //Check if the two words preceding the negation word and the proceeding one word are similar for both strings and
                //return true or false accordingly
                logger.log(Level.INFO, "Check if the two words preceding the negation word and the proceeding one word are similar for both strings and returning true or false accordingly");
                return (oneStringArray[indexOfSubstring - 1].equals(theOtherStringArray[indexOfOtherSubstring - 1]))
                        && (oneStringArray[indexOfSubstring - 2].equals(theOtherStringArray[indexOfOtherSubstring - 2]))
                        && (oneStringArray[indexOfSubstring + 1].equals(theOtherStringArray[indexOfOtherSubstring + 1]));

            } catch (Exception e) {
                logger.log(Level.INFO, "An error occurred while comparing the strings");
                return false;
            }

        } else if ((oneString.contains("n't") && theOtherString.contains("n't"))) {

            //Both strings contain negation phrase 'n't'
            logger.log(Level.INFO, "Both strings contain negation phrase 'n't'");

            //Note the index of the word containing the phrase
            logger.log(Level.INFO, "Noting the index of the word containing the phrase");

            for (Integer i = 0; i < oneStringArray.length; i++) {
                if (oneStringArray[i].contains("n't")) {
                    indexOfSubstring = i;
                    break;
                }
            }

            for (Integer i = 0; i < oneStringArray.length; i++) {
                if (oneStringArray[i].contains("n't")) {
                    indexOfOtherSubstring = i;
                    break;
                }
            }

            try {

                //Check if the two words preceding the negation word and the proceeding one word are similar for both strings and
                //return true or false accordingly
                logger.log(Level.INFO, "Check if the two words preceding the negation word and the proceeding one word are similar for both strings and returning true or false accordingly");
                return (oneStringArray[indexOfSubstring - 1].equals(theOtherStringArray[indexOfOtherSubstring - 1]))
                        && (oneStringArray[indexOfSubstring - 2].equals(theOtherStringArray[indexOfOtherSubstring - 2]))
                        && (oneStringArray[indexOfSubstring + 1].equals(theOtherStringArray[indexOfOtherSubstring + 1]));

            } catch (Exception e) {
                logger.log(Level.INFO, "An error occurred while comparing the strings");
                return false;
            }
            //</editor-fold>
            //<editor-fold defaultstate="collapsed" desc="Strings have no negations">
        } else {

            //The strings do not contain any negation word/phrase
            logger.log(Level.INFO, "The strings do not contain any negation word/phrase");

            //Strip the strings of keywords meant to be excluded
            logger.log(Level.INFO, "Stripping the strings of keywords meant to be excluded");

            for (ExcludedKeyword excludedKeyword : ExcludedKeyword.values()) {

                if (oneString.contains(excludedKeyword.getKeyword())) {
                    oneString = oneString.replace(excludedKeyword.getKeyword().trim(), "");
                }

                if (theOtherString.contains(excludedKeyword.getKeyword())) {
                    theOtherString = theOtherString.replace(excludedKeyword.getKeyword().trim(), "");
                }

            }

            logger.log(Level.INFO, "\nResulting one string is   : {0}", oneString);
            logger.log(Level.INFO, "Resulting other string is : {0}\n", theOtherString);

            if (oneStringArray.length == theOtherStringArray.length) {
                //<editor-fold defaultstate="collapsed" desc="Strings of equal length">

                //Both strings have equal number of words
                logger.log(Level.INFO, "Both strings have equal number of words");

                if (oneStringArray.length > 2) {

                    //Both strings have more than 2 words
                    logger.log(Level.INFO, "Both strings have more than 2 words");

                    //Put similar words and their indices in the array into a map
                    logger.log(Level.INFO, "Putting similar words and their indices in the array into a map");
                    for (Integer i = 0; i < oneStringArray.length; i++) {
                        for (Integer j = 0; j < theOtherStringArray.length; j++) {
                            if ((oneStringArray[i] != null || oneStringArray[i].trim().length() != 0) && (theOtherStringArray[j] != null || theOtherStringArray[j].trim().length() != 0)) {
                                if (oneStringArray[i].equals(theOtherStringArray[j])) {

                                    sameWordsInOneString.put(i, oneStringArray[i]);
                                    sameWordsInTheOtherString.put(j, theOtherStringArray[j]);

                                }
                            }
                        }
                    }

                    if (sameWordsInOneString.size() >= 3) {

                        //Both strings have more than 3 similar words
                        logger.log(Level.INFO, "Both strings have more than 3 similar words");

                        orderedWordsCount = 0;

                        for (Integer sameWordIndex : sameWordsInOneString.keySet()) {
                            try {
                                if (sameWordsInTheOtherString.get(sameWordIndex).equals(sameWordsInOneString.get(sameWordIndex))) {
                                    logger.log(Level.INFO, "Number of same words that are in order is: {0}", ++orderedWordsCount);
                                }
                            } catch (Exception e) {
                            }
                        }

                        if (orderedWordsCount >= 2) {

                            logger.log(Level.INFO, "The two strings are similar, returning true");
                            return true;

                        } else {

                            logger.log(Level.INFO, "The two strings are not similar, returning false");
                            return false;

                        }

                    } else if (sameWordsInOneString.size() == 2) {

                        //Both strings have 2 similar words
                        logger.log(Level.INFO, "Both strings have 2 similar words");

                        logger.log(Level.INFO, "Same in first string: {0}", sameWordsInOneString);
                        logger.log(Level.INFO, "Same in other string: {0}", sameWordsInTheOtherString);

                        logger.log(Level.INFO, "The two strings are similar, returning true");
                        return true;

                    }

                } else if (oneStringArray.length == 2) {

                    //Both strings have 2 words
                    logger.log(Level.INFO, "Both strings have 2 words");

                    //Put similar words and their indices in the array into a map
                    logger.log(Level.INFO, "Putting similar words and their indices in the array into a map");
                    for (Integer i = 0; i < oneStringArray.length; i++) {
                        for (Integer j = 0; j < theOtherStringArray.length; j++) {
                            if ((oneStringArray[i] != null || oneStringArray[i].trim().length() != 0) && (theOtherStringArray[j] != null || theOtherStringArray[j].trim().length() != 0)) {
                                if (oneStringArray[i].equals(theOtherStringArray[j])) {

                                    sameWordsInOneString.put(i, oneStringArray[i]);
                                    sameWordsInTheOtherString.put(j, theOtherStringArray[j]);

                                }
                            }
                        }
                    }

                    //Check if the similar words appear in the same order in the two strings
                    logger.log(Level.INFO, "Checking if the similar words appear in the same order in the two strings");

                    orderedWordsCount = 0;

                    for (Integer sameWordIndex : sameWordsInOneString.keySet()) {
                        try {
                            if (sameWordsInTheOtherString.get(sameWordIndex).equals(sameWordsInOneString.get(sameWordIndex))) {
                                logger.log(Level.INFO, "Number of same words that are in order is: {0}", ++orderedWordsCount);
                            }
                        } catch (Exception e) {
                        }
                    }

                    if (orderedWordsCount == 2) {

                        logger.log(Level.INFO, "The two strings are similar, returning true");
                        return true;

                    } else {

                        logger.log(Level.INFO, "The two strings are not similar, returning false");
                        return false;

                    }
                } else if (oneStringArray.length == 1) {

                    //Both strings have 1 word
                    logger.log(Level.INFO, "Both strings have 1 word");

                    //Put similar words and their indices in the array into a map
                    logger.log(Level.INFO, "Putting similar words and their indices in the array into a map");
                    for (Integer i = 0; i < oneStringArray.length; i++) {
                        for (Integer j = 0; j < theOtherStringArray.length; j++) {
                            if ((oneStringArray[i] != null || oneStringArray[i].trim().length() != 0) && (theOtherStringArray[j] != null || theOtherStringArray[j].trim().length() != 0)) {
                                if (oneStringArray[i].equals(theOtherStringArray[j])) {

                                    sameWordsInOneString.put(i, oneStringArray[i]);
                                    sameWordsInTheOtherString.put(j, theOtherStringArray[j]);

                                }
                            }
                        }
                    }

                    //Check if the similar words appear in the same order in the two strings
                    logger.log(Level.INFO, "Checking if the similar words appear in the same order in the two strings");

                    orderedWordsCount = 0;

                    for (Integer sameWordIndex : sameWordsInOneString.keySet()) {
                        try {
                            if (sameWordsInTheOtherString.get(sameWordIndex).equals(sameWordsInOneString.get(sameWordIndex))) {
                                logger.log(Level.INFO, "Number of same words that are in order is: {0}", ++orderedWordsCount);
                            }
                        } catch (Exception e) {
                        }
                    }

                    if (orderedWordsCount == 1) {

                        logger.log(Level.INFO, "The two strings are similar, returning true");
                        return true;

                    } else {

                        logger.log(Level.INFO, "The two strings are not similar, returning false");
                        return false;

                    }
                }

                //</editor-fold>
            } else {

                //Both strings have different number of words
                logger.log(Level.INFO, "Both strings have different number of words");

                if (oneStringArray.length < theOtherStringArray.length) {
                    //<editor-fold defaultstate="collapsed" desc="First string is shorter">

                    //First string is shorter
                    logger.log(Level.INFO, "First string is shorter");

                    shorterStringLength = oneStringArray.length;

                    if (shorterStringLength > 2) {

                        //The shorter string has more than two words
                        logger.log(Level.INFO, "The shorter string has more than two words");

                        //Put similar words and their indices in the array into a map
                        logger.log(Level.INFO, "Putting similar words and their indices in the array into a map");
                        for (Integer i = 0; i < oneStringArray.length; i++) {
                            for (Integer j = 0; j < theOtherStringArray.length; j++) {
                                if ((oneStringArray[i] != null || oneStringArray[i].trim().length() != 0) && (theOtherStringArray[j] != null || theOtherStringArray[j].trim().length() != 0)) {
                                    if (oneStringArray[i].equals(theOtherStringArray[j])) {

                                        sameWordsInOneString.put(i, oneStringArray[i]);
                                        sameWordsInTheOtherString.put(j, theOtherStringArray[j]);

                                    }
                                }
                            }
                        }

                        if (sameWordsInOneString.size() >= 3) {

                            //Both strings have more than 3 similar words
                            logger.log(Level.INFO, "Both strings have more than 3 similar words");

                            //Put the indices in the map into an array
                            logger.log(Level.INFO, "Putting the indices in the map into an array");
                            indexArrayOfSameInOne = sameWordsInOneString.keySet().toArray(new Integer[sameWordsInOneString.size()]);
                            indexArrayOfSameInTheOther = sameWordsInTheOtherString.keySet().toArray(new Integer[sameWordsInTheOtherString.size()]);

                            //Check if the similar words appear in the same order on  both strings
                            logger.log(Level.INFO, "Checking if the similar words appear in the same order on  both strings");
                            for (Integer i = 1; i < indexArrayOfSameInOne.length - 1; i++) {
                                if ((indexArrayOfSameInOne[i] + 1 == indexArrayOfSameInOne[i + 1]) && (indexArrayOfSameInOne[i] - 1 == indexArrayOfSameInOne[i - 1])) {

                                    for (Integer j = 1; j < indexArrayOfSameInTheOther.length - 1; j++) {
                                        if ((indexArrayOfSameInTheOther[j] + 1 == indexArrayOfSameInTheOther[j + 1]) && (indexArrayOfSameInTheOther[j] - 1 == indexArrayOfSameInTheOther[j - 1])) {

                                            logger.log(Level.INFO, "The two strings are similar, returning true");
                                            return true;
                                        }
                                    }

                                }
                            }

                            logger.log(Level.INFO, "The two strings are not similar, returning false");
                            return false;

                        } else if (sameWordsInOneString.size() == 2) {

                            //Both strings have 2 similar words
                            logger.log(Level.INFO, "Both strings have 2 similar words");

                            logger.log(Level.INFO, "Same in first string: {0}", sameWordsInOneString);
                            logger.log(Level.INFO, "Same in other string: {0}", sameWordsInTheOtherString);

                            logger.log(Level.INFO, "The two strings are similar, returning true");
                            return true;

                        }
                    } else if (shorterStringLength == 2) {

                        //The shorter string has two words
                        logger.log(Level.INFO, "The shorter string has two words");

                        //Put similar words and their indices in the array into a map
                        logger.log(Level.INFO, "Putting similar words and their indices in the array into a map");
                        for (Integer i = 0; i < oneStringArray.length; i++) {
                            for (Integer j = 0; j < theOtherStringArray.length; j++) {
                                if ((oneStringArray[i] != null || oneStringArray[i].trim().length() != 0) && (theOtherStringArray[j] != null || theOtherStringArray[j].trim().length() != 0)) {
                                    if (oneStringArray[i].equals(theOtherStringArray[j])) {

                                        sameWordsInOneString.put(i, oneStringArray[i]);
                                        sameWordsInTheOtherString.put(j, theOtherStringArray[j]);

                                    }
                                }
                            }
                        }

                        //Put the indices in the map into an array
                        logger.log(Level.INFO, "Putting the indices in the map into an array");
                        indexArrayOfSameInOne = sameWordsInOneString.keySet().toArray(new Integer[sameWordsInOneString.size()]);
                        indexArrayOfSameInTheOther = sameWordsInTheOtherString.keySet().toArray(new Integer[sameWordsInTheOtherString.size()]);

                        //Check if the similar words appear in the same order on  both strings
                        logger.log(Level.INFO, "Checking if the similar words appear in the same order on  both strings");
                        for (Integer i = 0; i < indexArrayOfSameInOne.length - 1; i++) {
                            for (Integer j = 0; j < indexArrayOfSameInTheOther.length - 1; j++) {

                                if ((indexArrayOfSameInOne[i + 1] - indexArrayOfSameInOne[i]) == (indexArrayOfSameInTheOther[j + 1] - indexArrayOfSameInTheOther[j])) {

                                    logger.log(Level.INFO, "The two strings are similar, returning true");
                                    return true;

                                }
                            }
                        }

                        logger.log(Level.INFO, "The two strings are not similar, returning false");
                        return false;

                    } else if (shorterStringLength == 1) {

                        //The shorter string has one word
                        logger.log(Level.INFO, "The shorter string has one word");

                        //Check if the similar word appears in the longer string
                        logger.log(Level.INFO, "Checking if the similar word appear in the longer string");
                        for (String oneStringArrayItem : oneStringArray) {
                            for (String theOtherStringArrayItem : theOtherStringArray) {
                                if ((oneStringArrayItem != null) && (theOtherStringArrayItem != null)) {
                                    if (oneStringArrayItem.equals(theOtherStringArrayItem)) {
                                        logger.log(Level.INFO, "The two strings are similar, returning true");
                                        return true;
                                    }
                                }
                            }
                        }

                        logger.log(Level.INFO, "The two strings are not similar, returning false");
                        return false;

                    }

                    //</editor-fold>
                } else {
                    //<editor-fold defaultstate="collapsed" desc="Second string is shorter">

                    //Second string is shorter
                    logger.log(Level.INFO, "Second string is shorter");

                    shorterStringLength = theOtherStringArray.length;

                    if (shorterStringLength > 2) {

                        //The shorter string has more than two words
                        logger.log(Level.INFO, "The shorter string has more than two words");

                        //Put similar words and their indices in the array into a map
                        logger.log(Level.INFO, "Putting similar words and their indices in the array into a map");
                        for (Integer j = 0; j < theOtherStringArray.length; j++) {
                            for (Integer i = 0; i < oneStringArray.length; i++) {
                                if ((theOtherStringArray[j] != null || theOtherStringArray[j].trim().length() != 0) && (oneStringArray[i] != null || oneStringArray[i].trim().length() != 0)) {
                                    if (theOtherStringArray[j].equals(oneStringArray[i])) {

                                        sameWordsInOneString.put(i, oneStringArray[i]);
                                        sameWordsInTheOtherString.put(j, theOtherStringArray[j]);

                                    }
                                }
                            }
                        }

                        if (sameWordsInTheOtherString.size() >= 3) {

                            //Both strings have more than 3 similar words
                            logger.log(Level.INFO, "Both strings have more than 3 similar words");

                            //Put the indices in the map into an array
                            logger.log(Level.INFO, "Putting the indices in the map into an array");
                            indexArrayOfSameInOne = sameWordsInOneString.keySet().toArray(new Integer[sameWordsInOneString.size()]);
                            indexArrayOfSameInTheOther = sameWordsInTheOtherString.keySet().toArray(new Integer[sameWordsInTheOtherString.size()]);

                            //Check if the similar words appear in the same order on  both strings
                            logger.log(Level.INFO, "Checking if the similar words appear in the same order on  both strings");
                            for (Integer j = 1; j < indexArrayOfSameInTheOther.length - 1; j++) {
                                if ((indexArrayOfSameInTheOther[j] + 1 == indexArrayOfSameInTheOther[j + 1]) && (indexArrayOfSameInTheOther[j] - 1 == indexArrayOfSameInTheOther[j - 1])) {

                                    for (Integer i = 1; i < indexArrayOfSameInOne.length - 1; i++) {
                                        if ((indexArrayOfSameInOne[i] + 1 == indexArrayOfSameInOne[i + 1]) && (indexArrayOfSameInOne[i] - 1 == indexArrayOfSameInOne[i - 1])) {

                                            logger.log(Level.INFO, "The two strings are similar, returning true");
                                            return true;
                                        }
                                    }

                                }
                            }

                            logger.log(Level.INFO, "The two strings are not similar, returning false");
                            return false;

                        } else if (sameWordsInTheOtherString.size() == 2) {

                            //Both strings have 2 similar words
                            logger.log(Level.INFO, "Both strings have 2 similar words");

                            //Put the indices in the map into an array
                            logger.log(Level.INFO, "Putting the indices in the map into an array");
                            indexArrayOfSameInOne = sameWordsInOneString.keySet().toArray(new Integer[sameWordsInOneString.size()]);
                            indexArrayOfSameInTheOther = sameWordsInTheOtherString.keySet().toArray(new Integer[sameWordsInTheOtherString.size()]);

                            //Check if the similar words appear in the same order on  both strings
                            logger.log(Level.INFO, "Checking if the similar words appear in the same order on  both strings");
                            for (Integer j = 1; j < indexArrayOfSameInTheOther.length - 1; j++) {
                                if ((indexArrayOfSameInTheOther[j] + 1 == indexArrayOfSameInTheOther[j + 1]) && (indexArrayOfSameInTheOther[j] - 1 == indexArrayOfSameInTheOther[j - 1])) {

                                    for (Integer i = 1; i < indexArrayOfSameInOne.length - 1; i++) {
                                        if ((indexArrayOfSameInOne[i] + 1 == indexArrayOfSameInOne[i + 1]) && (indexArrayOfSameInOne[i] - 1 == indexArrayOfSameInOne[i - 1])) {

                                            logger.log(Level.INFO, "The two strings are similar, returning true");
                                            return true;
                                        }
                                    }

                                }
                            }

                            logger.log(Level.INFO, "The two strings are not similar, returning false");
                            return false;

                        }
                    } else if (shorterStringLength == 2) {

                        //The shorter string has two words
                        logger.log(Level.INFO, "The shorter string has two words");

                        //Put similar words and their indices in the array into a map
                        logger.log(Level.INFO, "Putting similar words and their indices in the array into a map");
                        for (Integer j = 0; j < theOtherStringArray.length; j++) {
                            for (Integer i = 0; i < oneStringArray.length; i++) {
                                if ((theOtherStringArray[j] != null || theOtherStringArray[j].trim().length() != 0) && (oneStringArray[i] != null || oneStringArray[i].trim().length() != 0)) {
                                    if (theOtherStringArray[j].equals(oneStringArray[i])) {

                                        sameWordsInOneString.put(i, oneStringArray[i]);
                                        sameWordsInTheOtherString.put(j, theOtherStringArray[j]);

                                    }
                                }
                            }
                        }

                        //Put the indices in the map into an array
                        logger.log(Level.INFO, "Putting the indices in the map into an array");
                        indexArrayOfSameInOne = sameWordsInOneString.keySet().toArray(new Integer[sameWordsInOneString.size()]);
                        indexArrayOfSameInTheOther = sameWordsInTheOtherString.keySet().toArray(new Integer[sameWordsInTheOtherString.size()]);

                        //Check if the similar words appear in the same order on  both strings
                        logger.log(Level.INFO, "Checking if the similar words appear in the same order on  both strings");
                        for (Integer j = 0; j < indexArrayOfSameInTheOther.length - 1; j++) {
                            for (Integer i = 0; i < indexArrayOfSameInOne.length - 1; i++) {

                                if ((indexArrayOfSameInTheOther[j + 1] - indexArrayOfSameInTheOther[j]) == (indexArrayOfSameInOne[i + 1] - indexArrayOfSameInOne[i])) {

                                    logger.log(Level.INFO, "The two strings are similar, returning true");
                                    return true;

                                }
                            }
                        }

                        logger.log(Level.INFO, "The two strings are not similar, returning false");
                        return false;

                    } else if (shorterStringLength == 1) {

                        //The shorter string has one word
                        logger.log(Level.INFO, "The shorter string has one word");

                        //Check if the similar word appears in the longer string
                        logger.log(Level.INFO, "Checking if the similar word appear in the longer string");
                        for (String theOtherStringArrayItem : theOtherStringArray) {
                            for (String oneStringArrayItem : oneStringArray) {
                                if ((theOtherStringArrayItem != null) && (oneStringArrayItem != null)) {
                                    if (theOtherStringArrayItem.equals(oneStringArrayItem)) {
                                        logger.log(Level.INFO, "The two strings are similar, returning true");
                                        return true;
                                    }
                                }
                            }
                        }

                        logger.log(Level.INFO, "The two strings are not similar, returning false");
                        return false;

                    }
                    //</editor-fold>
                }

            }

            logger.log(Level.INFO, "The two strings are not similar, returning false");
            return false;
        }
        //</editor-fold>
    }

    //</editor-fold> 
    private enum ExcludedKeyword {
        //<editor-fold defaultstate="collapsed" desc="Excluded keywords">

        I(" i "),
        IS(" is "),
        THE_("the "),
        THE(" the "),
        WAS(" was "),
        HAS(" has "),
        HAVE(" have "),
        DOES(" does "),
        COULD(" could "),
        WOULD(" would "),
        THINK(" think "),
        COURSE(" course "),
        SHOULD(" should "),
        LECTURER(" lecturer ");

        private ExcludedKeyword(String keyword) {
            this.keyword = keyword;
        }

        private final String keyword;

        /**
         * @return the keyword
         */
        public String getKeyword() {
            return keyword;
        }

        //</editor-fold>
    }
//<editor-fold defaultstate="collapsed" desc="Convert">

    private AssessedEvaluationDetails convertAssessedEvaluationToAssessedEvaluationDetails(AssessedEvaluation assessedEvaluation) {
        //Entered method for converting assessed evaluation to assessed evaluation details
        logger.log(Level.FINE, "Entered method for converting assessedEvaluations to assessed evaluation details");

        //Convert list of assessed evaluation to assessed evaluation details
        logger.log(Level.FINE, "Convert list of assessed evaluation to assessed evaluation details");

        questionCategoryDetails = new QuestionCategoryDetails();
        questionCategoryDetails.setId(assessedEvaluation.getQuestionCategory().getId());

        evaluatedQuestionDetails = new EvaluatedQuestionDetails();
        evaluatedQuestionDetails.setId(assessedEvaluation.getEvaluatedQuestion().getId());

        evaluationSessionDetails = new EvaluationSessionDetails();
        evaluationSessionDetails.setId(assessedEvaluation.getEvaluationSession().getId());

        courseOfSessionDetails = new CourseOfSessionDetails();
        courseOfSessionDetails.setId(assessedEvaluation.getCourseOfSession().getId());

        AssessedEvaluationDetails details = new AssessedEvaluationDetails();
        details.setId(assessedEvaluation.getId());
        details.setRating(assessedEvaluation.getRating());
        details.setActive(assessedEvaluation.getActive());
        details.setCourseOfSession(courseOfSessionDetails);
        details.setVersion(assessedEvaluation.getVersion());
        details.setQuestionCategory(questionCategoryDetails);
        details.setEvaluatedQuestion(evaluatedQuestionDetails);
        details.setEvaluationSession(evaluationSessionDetails);
        details.setPercentageScore(assessedEvaluation.getPercentageScore());
        details.setQuestionDescription(assessedEvaluation.getQuestionDescription());

        //Returning converted assessed evaluation details
        logger.log(Level.FINE, "Returning converted assessed evaluation details");
        return details;
    }

//</editor-fold>
    private static final Logger logger = Logger.getLogger(EvaluationMarkingRequests.class.getSimpleName());

}
