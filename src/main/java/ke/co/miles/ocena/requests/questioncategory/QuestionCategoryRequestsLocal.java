/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.questioncategory;

import java.util.List;
import javax.ejb.Local;
import ke.co.miles.ocena.entities.QuestionCategory;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.QuestionCategoryDetails;

/**
 *
 * @author Ben Siech
 */
@Local
public interface QuestionCategoryRequestsLocal {

    /**
     *
     * @param details
     * @return
     * @throws InvalidArgumentException
     */
    public Short addQuestionCategory(QuestionCategoryDetails details) throws InvalidArgumentException;

    /**
     *
     * @return @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public List<QuestionCategoryDetails> retrieveQuestionCategories() throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param details
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void editQuestionCategory(QuestionCategoryDetails details) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param id
     * @throws InvalidArgumentException
     * @throws InvalidStateException
     */
    public void removeQuestionCategory(Short id) throws InvalidArgumentException, InvalidStateException;

    /**
     *
     * @param questionCategory
     * @return
     */
    public QuestionCategoryDetails convertQuestionCategoryToQuestionCategoryDetails(QuestionCategory questionCategory);

}
