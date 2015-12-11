/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ke.co.miles.ocena.requests.degree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import ke.co.miles.ocena.defaults.EntityRequests;
import ke.co.miles.ocena.entities.Department;
import ke.co.miles.ocena.entities.Faculty;
import ke.co.miles.ocena.entities.Degree;
import ke.co.miles.ocena.entities.Admission;
import ke.co.miles.ocena.exceptions.InvalidArgumentException;
import ke.co.miles.ocena.exceptions.InvalidStateException;
import ke.co.miles.ocena.utilities.DepartmentDetails;
import ke.co.miles.ocena.utilities.FacultyDetails;
import ke.co.miles.ocena.utilities.AdmissionDetails;
import ke.co.miles.ocena.utilities.DegreeDetails;

/**
 *
 * @author Ben Siech
 */
@Stateless
public class DegreeRequests extends EntityRequests implements DegreeRequestsLocal {

//<editor-fold defaultstate="collapsed" desc="Create">
    @Override
    public Integer addDegree(DegreeDetails details) throws InvalidArgumentException {
        //Method for adding a degree record to the database
        logger.log(Level.INFO, "Entered the method for adding a degree record to the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_019_01");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The degree name is null");
            throw new InvalidArgumentException("error_019_02");
        } else if (details.getName().trim().length() > 120) {
            logger.log(Level.INFO, "The degree name is longer than 120 characters");
            throw new InvalidArgumentException("error_019_03");
        } else if (details.getAdmission() == null) {
            logger.log(Level.INFO, "The admission which offers the degree is null");
            throw new InvalidArgumentException("error_019_04");
        } else if (details.getDepartment() == null && details.getFaculty() == null) {
            logger.log(Level.INFO, "The department and faculty are null");
            throw new InvalidArgumentException("error_019_05");
        }

        //Checking if the degree is a duplicate
        logger.log(Level.INFO, "Checking if the degree name is a duplicate");
        q = em.createNamedQuery("Degree.findByName");

        q.setParameter("name", details.getName());
        try {
            degree = (Degree) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Degree name is available for use");
            degree = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (degree
                != null) {
            logger.log(Level.SEVERE, "Degree name is already in use");
            throw new InvalidArgumentException("error_019_06");
        }

        //Creating a container to hold degree record
        logger.log(Level.INFO, "Creating a container to hold degree record");
        degree = new Degree();
        degree.setName(details.getName());
        degree.setActive(details.getActive());
        degree.setAdmission(em.find(Admission.class, details.getAdmission().getId()));
        //See if the degree is offered by a faculty
        try {
            degree.setFaculty(em.find(Faculty.class, details.getFaculty().getId()));
        } catch (Exception e) {
            logger.log(Level.INFO, "The degree is not offered in a faculty");
        }
        //See if the degree is offered by a department
        try {
            degree.setDepartment(em.find(Department.class, details.getDepartment().getId()));
        } catch (Exception e) {
            logger.log(Level.INFO, "The degree is not offered in a department");
        }

        //Adding a degree record to the database
        logger.log(Level.INFO, "Adding a degree record to the database");
        try {
            em.persist(degree);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record creation", e);
            throw new EJBException("error_000_01");
        }

        //Returning the unique identifier of the new record added
        logger.log(Level.INFO, "Returning the unique identifier of the new record added");
        return degree.getId();

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Read">

    @Override
    public List<DegreeDetails> retrieveDegreesOfFacultyOrDepartmentAndAdmission(Object object, Integer admissionId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a list of degrees offered in a faculty by admission
        logger.log(Level.INFO, "Entered the method for retrieving a list of degrees offered in a faculty by admission");

        if (object == null) {
            logger.log(Level.INFO, "The department or faculty is null");
            throw new InvalidArgumentException("error_019_05");
        } else if (admissionId == null) {
            logger.log(Level.INFO, "The unique identfier of the admission is null");
            throw new InvalidArgumentException("error_008_05");
        }

        //Retrieve the degree records from the database
        logger.log(Level.INFO, "Retrieving the degree records from the database");
        List<Degree> degrees = new ArrayList<>();
        if (object instanceof FacultyDetails) {
            q = em.createNamedQuery("Degree.findByAdmissionIdAndFacultyId");
            facultyDetails = (FacultyDetails) object;
            q.setParameter("facultyId", facultyDetails.getId());
        } else if (object instanceof DepartmentDetails) {
            q = em.createNamedQuery("Degree.findByAdmissionIdAndDepartmentId");
            departmentDetails = (DepartmentDetails) object;
            q.setParameter("departmentId", departmentDetails.getId());
        }
        q.setParameter("admissionId", admissionId);

        try {
            degrees = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.INFO, "An error occurred while retrieving the degree records ");
            throw new InvalidStateException("error_000_01");
        }

        //Return the list
        logger.log(Level.INFO, "Returning the list of degrees");
        return convertDegreesToDegreeDetailsList(degrees);
    }

    @Override
    public DegreeDetails retrieveDegree(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a unique degree record from the database
        logger.log(Level.INFO, "Entered the method for retrieving a unique degree record from the database");

        //Retrieving degree records from the database
        logger.log(Level.INFO, "Retrieving degree records from the database");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier of the degree is null");
            throw new InvalidArgumentException("error_019_07");
        }

        //Retrieving degree records from the database
        logger.log(Level.INFO, "Retrieving degree records from the database");
        q = em.createNamedQuery("Degree.findById");
        q.setParameter("id", id);
        degree = new Degree();
        try {
            degree = (Degree) q.getSingleResult();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the degree record
        logger.log(Level.INFO, "Returning the degree record");
        return convertDegreeToDegreeDetails(degree);
    }

    @Override
    public List<DegreeDetails> retrieveDegrees(Integer admissionId) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving degree records from the database
        logger.log(Level.INFO, "Entered the method for retrieving degree records from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the admission unique identifier passed in");
        if (admissionId == null) {
            logger.log(Level.INFO, "The unique identifier of the admission which offers the degree is null");
            throw new InvalidArgumentException("error_019_08");
        }

        //Retrieving degree records from the database
        logger.log(Level.INFO, "Retrieving degree records from the database");
        q = em.createNamedQuery("Degree.findByAdmissionId");
        q.setParameter("admissionId", admissionId);
        List<Degree> degrees = new ArrayList<>();
        try {
            degrees = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of degree records
        logger.log(Level.INFO, "Returning the details list of degree records");
        return convertDegreesToDegreeDetailsList(degrees);
    }

    @Override
    public List<DegreeDetails> retrieveFacultyDegrees(Integer facultyId) throws InvalidArgumentException {
        //Method for retrieving degrees of a faculty from the database
        logger.log(Level.INFO, "Entered the method for retrieving degrees of a faculty from the database");

        //Ascertain validity of the faculty unique identifier
        logger.log(Level.INFO, "Ascertaining validity of the faculty unique identifier");
        if (facultyId == null) {
            logger.log(Level.INFO, "Faculty unique identifier is null");
            throw new InvalidArgumentException("error_019_09");
        }

        //Retrieving degree records from the database
        logger.log(Level.INFO, "Retrieving degree records from the database");
        q = em.createNamedQuery("Degree.findByFacultyId");
        q.setParameter("facultyId", facultyId);
        List<Degree> degrees = new ArrayList<>();
        try {
            degrees = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of degree records
        logger.log(Level.INFO, "Returning the details list of degree records");
        return convertDegreesToDegreeDetailsList(degrees);
    }

    @Override
    public List<DegreeDetails> retrieveDepartmentDegrees(Integer departmentId) throws InvalidArgumentException {
        //Method for retrieving degrees of a department from the database
        logger.log(Level.INFO, "Entered the method for retrieving degrees of a department from the database");

        //Ascertain validity of the department unique identifier
        logger.log(Level.INFO, "Ascertaining validity of the department unique identifier");
        if (departmentId == null) {
            logger.log(Level.INFO, "Department unique identifier is null");
            throw new InvalidArgumentException("error_019_10");
        }

        //Retrieving degree records from the database
        logger.log(Level.INFO, "Retrieving degree records from the database");
        q = em.createNamedQuery("Degree.findByDepartmentId");
        q.setParameter("departmentId", departmentId);
        List<Degree> degrees = new ArrayList<>();
        try {
            degrees = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Returning the details list of degree records
        logger.log(Level.INFO, "Returning the details list of degree records");
        return convertDegreesToDegreeDetailsList(degrees);
    }

    @Override
    public Map<AdmissionDetails, List<DegreeDetails>> retrieveDegreesOfFacultyByAdmission(FacultyDetails faculty) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a map of degrees in degree category of records of a faculty in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of degrees in degree category of records records of a faculty in the database");

        //Ascertain validity of the faculty
        logger.log(Level.INFO, "Ascertaining validity of the faculty");
        if (faculty == null) {
            logger.log(Level.INFO, "Faculty is null");
            throw new InvalidArgumentException("error_019_11");
        }

        //Retrieving degree category records from the database
        logger.log(Level.INFO, "Retrieving the degree category records from the database");
        q = em.createNamedQuery("Admission.findAll");
        List<Admission> admissions = new ArrayList<>();
        try {
            admissions = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Retrieve degrees in categories from the database and put them in a map
        logger.log(Level.INFO, "Retrieving degrees in categories records of a faculty from the database and putting them in a map");
        Map<AdmissionDetails, List<DegreeDetails>> degreesInAdmissionMap = new HashMap<>();
        q = em.createNamedQuery("Degree.findByAdmissionIdAndFacultyId");
        for (Admission a : admissions) {
            q.setParameter("admissionId", a.getId());
            q.setParameter("facultyId", faculty.getId());
            degreesInAdmissionMap.put(admissionService.convertAdmissionToAdmissionDetails(a),
                    convertDegreesToDegreeDetailsList(q.getResultList()));
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of degrees in degree categories records of a faculty ");
        return degreesInAdmissionMap;
    }

    @Override
    public Map<AdmissionDetails, List<DegreeDetails>> retrieveDegreesOfDepartmentByAdmission(DepartmentDetails department) throws InvalidArgumentException, InvalidStateException {
        //Method for retrieving a map of degrees in degree category of records of a department in the database
        logger.log(Level.INFO, "Entered the method for retrieving a map of degrees in degree category of records records of a department in the database");

        //Ascertain validity of the department
        logger.log(Level.INFO, "Ascertaining validity of the department");
        if (faculty == null) {
            logger.log(Level.INFO, "Department is null");
            throw new InvalidArgumentException("error_019_12");
        }

        //Retrieving degree category records from the database
        logger.log(Level.INFO, "Retrieving the degree category records from the database");
        q = em.createNamedQuery("Admission.findAll");
        List<Admission> admissions = new ArrayList<>();
        try {
            admissions = q.getResultList();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }

        //Retrieve degrees in categories from the database and put them in a map
        logger.log(Level.INFO, "Retrieving degrees in categories records of a department from the database and putting them in a map");
        Map<AdmissionDetails, List<DegreeDetails>> degreesInAdmissionMap = new HashMap<>();
        q = em.createNamedQuery("Degree.findByAdmissionIdAndDepartmentId");
        for (Admission qc : admissions) {
            q.setParameter("admissionId", qc.getId());
            q.setParameter("departmentId", department.getId());
            degreesInAdmissionMap.put(admissionService.convertAdmissionToAdmissionDetails(qc),
                    convertDegreesToDegreeDetailsList(q.getResultList()));
        }

        //Return the map
        logger.log(Level.INFO, "Returning the map of degrees in degree categories records of a department ");
        return degreesInAdmissionMap;
    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Update">
    @Override
    public void editDegree(DegreeDetails details) throws InvalidArgumentException, InvalidStateException {
        //Method for editing a degree record in the database
        logger.log(Level.INFO, "Entered the method for editing a degree record in the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the details passed in");
        if (details == null) {
            logger.log(Level.INFO, "The details are null");
            throw new InvalidArgumentException("error_019_01");
        } else if (details.getId() == null) {
            logger.log(Level.INFO, "The degree's unique identifier is null");
            throw new InvalidArgumentException("error_019_07");
        } else if (details.getName() == null || details.getName().trim().length() == 0) {
            logger.log(Level.INFO, "The degree name is null");
            throw new InvalidArgumentException("error_019_02");
        } else if (details.getName().trim().length() > 120) {
            logger.log(Level.INFO, "The degree name is longer than 120 characters");
            throw new InvalidArgumentException("error_019_03");
        } else if (details.getAdmission() == null) {
            logger.log(Level.INFO, "The admission which offers the degree is null");
            throw new InvalidArgumentException("error_019_04");
        } else if (details.getDepartment() == null && details.getFaculty() == null) {
            logger.log(Level.INFO, "The department and faculty are null");
            throw new InvalidArgumentException("error_019_05");
        }

        //Checking if the degree is a duplicate
        logger.log(Level.INFO, "Checking if the degree name is a duplicate");
        q = em.createNamedQuery("Degree.findByName");
        q.setParameter("name", details.getName());
        try {
            degree = (Degree) q.getSingleResult();
        } catch (NoResultException e) {
            logger.log(Level.INFO, "Degree name is available for use");
            degree = null;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record retrieval", e);
            throw new EJBException("error_000_01");
        }
        if (degree != null) {
            if (!(degree.getId().equals(details.getId()))) {
                logger.log(Level.SEVERE, "Degree name is already in use");
                throw new InvalidArgumentException("error_019_06");
            }
        }

        //Creating a container to hold degree record
        logger.log(Level.INFO, "Creating a container to hold degree record");
        degree = em.find(Degree.class, details.getId());
        degree.setId(details.getId());
        degree.setName(details.getName());
        degree.setActive(details.getActive());
        degree.setAdmission(em.find(Admission.class, details.getAdmission().getId()));
        //See if the degree is offered by a faculty
        try {
            degree.setFaculty(em.find(Faculty.class, details.getFaculty().getId()));
        } catch (Exception e) {
            logger.log(Level.INFO, "The degree is not offered in a faculty");
        }
        //See if the degree is offered by a department
        try {
            degree.setDepartment(em.find(Department.class, details.getDepartment().getId()));
        } catch (Exception e) {
            logger.log(Level.INFO, "The degree is not offered in a department");
        }

        //Editing a degree record in the database
        logger.log(Level.INFO, "Editing a degree record in the database");
        try {
            em.merge(degree);
            em.flush();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record update", e);
            throw new InvalidStateException("error_000_01");
        }

    }

//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Delete">
    @Override
    public void removeDegree(Integer id) throws InvalidArgumentException, InvalidStateException {
        //Method for removing a degree record from the database
        logger.log(Level.INFO, "Entered the method for removing a degree record from the database");

        //Checking validity of details
        logger.log(Level.INFO, "Checking validity of the unique identifier passed in");
        if (id == null) {
            logger.log(Level.INFO, "The unique identifier of the degree record is null");
            throw new InvalidArgumentException("error_019_07");
        }

        //Removing a degree record from the database
        logger.log(Level.INFO, "Removing a degree record from the database");
        degree = em.find(Degree.class, id);
        try {
            em.remove(degree);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An error occurred during record removal", e);
            throw new InvalidStateException("error_000_01");
        }

    }
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc="Convert">

    private List<DegreeDetails> convertDegreesToDegreeDetailsList(List<Degree> degrees) {
        //Entered method for converting degrees list to degree details list
        logger.log(Level.FINE, "Entered method for converting degrees list to degree details list");

        //Convert list of degrees to degree details list
        logger.log(Level.FINE, "Convert list of degrees to degree details list");
        List<DegreeDetails> details = new ArrayList<>();
        for (Degree d : degrees) {
            details.add(convertDegreeToDegreeDetails(d));
        }

        //Returning converted degree details list
        logger.log(Level.FINE, "Returning converted degree details list");
        return details;
    }

    @Override
    public DegreeDetails convertDegreeToDegreeDetails(Degree degree) {
        //Entered method for converting degree to degree details
        logger.log(Level.FINE, "Entered method for converting degrees to degree details");

        //Convert list of degree to degree details
        logger.log(Level.FINE, "Convert list of degree to degree details");
        admissionDetails = new AdmissionDetails();
        admissionDetails.setId(degree.getAdmission().getId());
        admissionDetails.setAdmission(degree.getAdmission().getAdmission());

        departmentDetails = new DepartmentDetails();
        try {
            departmentDetails.setId(degree.getDepartment().getId());
        } catch (Exception e) {
            logger.log(Level.FINE, "The degree is not offered in a department");
            departmentDetails = null;
        }

        facultyDetails = new FacultyDetails();
        try {
            facultyDetails.setId(degree.getFaculty().getId());
        } catch (Exception e) {
            logger.log(Level.FINE, "The degree is not offered in a faculty");
            facultyDetails = null;
        }

        DegreeDetails details = new DegreeDetails();
        details.setDepartment(departmentDetails);
        details.setVersion(degree.getVersion());
        details.setAdmission(admissionDetails);
        details.setActive(degree.getActive());
        details.setFaculty(facultyDetails);
        details.setName(degree.getName());
        details.setId(degree.getId());

        //Returning converted degree details
        logger.log(Level.FINE, "Returning converted degree details");
        return details;
    }
//</editor-fold>
    private static final Logger logger = Logger.getLogger(DegreeRequests.class.getSimpleName());

}
