SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `ocena` ;
CREATE SCHEMA IF NOT EXISTS `ocena` DEFAULT CHARACTER SET latin1 ;
SHOW WARNINGS;
USE `ocena` ;

-- -----------------------------------------------------
-- Table `country`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `country` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `country` (
  `id` SMALLINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `iso` CHAR(2) NOT NULL,
  `name` VARCHAR(80) NOT NULL,
  `nice_name` VARCHAR(80) NOT NULL,
  `iso3` CHAR(3) NULL,
  `num_code` SMALLINT(6) NULL,
  `phone_code` INT NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idcountry_UNIQUE` (`id` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `institution`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `institution` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `institution` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  `abbreviation` VARCHAR(20) NOT NULL,
  `country` SMALLINT(3) UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  INDEX `fk_institution_country2_idx` (`country` ASC),
  CONSTRAINT `fk_institution_country2`
    FOREIGN KEY (`country`)
    REFERENCES `country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `college`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `college` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `college` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(300) NOT NULL,
  `abbreviation` VARCHAR(20) NOT NULL,
  `institution` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idcollege_UNIQUE` (`id` ASC),
  UNIQUE INDEX `collegecol_UNIQUE` (`name` ASC),
  UNIQUE INDEX `abbreviation_UNIQUE` (`abbreviation` ASC),
  INDEX `fk_college_institution_idx` (`institution` ASC),
  CONSTRAINT `fk_college_institution`
    FOREIGN KEY (`institution`)
    REFERENCES `institution` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `contact` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `contact` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `faculty`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `faculty` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `faculty` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(300) NOT NULL,
  `abbreviation` VARCHAR(20) NOT NULL,
  `college` INT UNSIGNED NOT NULL,
  `contact` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  UNIQUE INDEX `abbreviation_UNIQUE` (`abbreviation` ASC),
  INDEX `fk_school_college1_idx` (`college` ASC),
  INDEX `fk_school_contact1_idx` (`contact` ASC),
  CONSTRAINT `fk_school_college1`
    FOREIGN KEY (`college`)
    REFERENCES `college` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_contact1`
    FOREIGN KEY (`contact`)
    REFERENCES `contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `department`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `department` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `department` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(300) NOT NULL,
  `abbreviation` VARCHAR(20) NOT NULL,
  `faculty` INT UNSIGNED NOT NULL,
  `contact` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  UNIQUE INDEX `abbreviation_UNIQUE` (`abbreviation` ASC),
  INDEX `fk_department_faculty1_idx` (`faculty` ASC),
  INDEX `fk_department_contact1_idx` (`contact` ASC),
  CONSTRAINT `fk_department_faculty1`
    FOREIGN KEY (`faculty`)
    REFERENCES `faculty` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_department_contact1`
    FOREIGN KEY (`contact`)
    REFERENCES `contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `phone_contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `phone_contact` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `phone_contact` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `mobile_number` VARCHAR(20) NULL,
  `fixed_number` VARCHAR(20) NULL,
  `contact` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `mobile_number_UNIQUE` (`mobile_number` ASC),
  INDEX `fk_phone_contact_contact1_idx` (`contact` ASC),
  CONSTRAINT `fk_phone_contact_contact1`
    FOREIGN KEY (`contact`)
    REFERENCES `contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `postal_contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `postal_contact` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `postal_contact` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `box_number` VARCHAR(20) NULL,
  `postal_code` VARCHAR(20) NULL,
  `town` VARCHAR(100) NULL,
  `country` SMALLINT(3) UNSIGNED NULL,
  `contact` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idpostal_contact_UNIQUE` (`id` ASC),
  INDEX `fk_postal_contact_contact1_idx` (`contact` ASC),
  INDEX `fk_postal_contact_country1_idx` (`country` ASC),
  CONSTRAINT `fk_postal_contact_contact1`
    FOREIGN KEY (`contact`)
    REFERENCES `contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_postal_contact_country1`
    FOREIGN KEY (`country`)
    REFERENCES `country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `email_contact`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `email_contact` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `email_contact` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `email_address` VARCHAR(65) NOT NULL,
  `contact` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `email_address_UNIQUE` (`email_address` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_email_contact_contact1_idx` (`contact` ASC),
  CONSTRAINT `fk_email_contact_contact1`
    FOREIGN KEY (`contact`)
    REFERENCES `contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `person` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `person` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `contact` INT UNSIGNED NOT NULL,
  `reference_number` VARCHAR(20) NOT NULL,
  `national_id_or_passport` VARCHAR(20) NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `reference_number_UNIQUE` (`reference_number` ASC),
  INDEX `fk_person_contact1_idx` (`contact` ASC),
  UNIQUE INDEX `national_id_UNIQUE` (`national_id_or_passport` ASC),
  CONSTRAINT `fk_person_contact1`
    FOREIGN KEY (`contact`)
    REFERENCES `contact` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `faculty_member_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `faculty_member_role` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `faculty_member_role` (
  `id` SMALLINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `faculty_member_role` VARCHAR(20) NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idrole_UNIQUE` (`id` ASC),
  UNIQUE INDEX `role_UNIQUE` (`faculty_member_role` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `faculty_member`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `faculty_member` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `faculty_member` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `person` INT UNSIGNED NOT NULL,
  `faculty` INT UNSIGNED NULL,
  `department` INT UNSIGNED NULL,
  `admission_year` DATE NULL,
  `faculty_member_role` SMALLINT(3) UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  INDEX `fk_person_has_school_person1_idx` (`person` ASC),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_school_member_role1_idx` (`faculty_member_role` ASC),
  INDEX `fk_faculty_member_faculty1_idx` (`faculty` ASC),
  INDEX `fk_faculty_member_department1_idx` (`department` ASC),
  CONSTRAINT `fk_person_has_school_person1`
    FOREIGN KEY (`person`)
    REFERENCES `person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_school_member_role1`
    FOREIGN KEY (`faculty_member_role`)
    REFERENCES `faculty_member_role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_faculty_member_faculty1`
    FOREIGN KEY (`faculty`)
    REFERENCES `faculty` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_faculty_member_department1`
    FOREIGN KEY (`department`)
    REFERENCES `department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `user_group`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_group` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `user_group` (
  `id` SMALLINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `user_group` VARCHAR(45) NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `user_group_UNIQUE` (`user_group` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `user_account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user_account` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `user_account` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(20) NOT NULL,
  `password` VARCHAR(150) NOT NULL,
  `person` INT UNSIGNED NOT NULL,
  `user_group` SMALLINT(3) UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `active_from` DATETIME NULL,
  `deactivated_on` DATETIME NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC),
  INDEX `fk__user_user_group1_idx` (`user_group` ASC),
  INDEX `fk__user_person1_idx` (`person` ASC),
  CONSTRAINT `fk__user_user_group1`
    FOREIGN KEY (`user_group`)
    REFERENCES `user_group` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk__user_person1`
    FOREIGN KEY (`person`)
    REFERENCES `person` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `question_category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `question_category` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `question_category` (
  `id` SMALLINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(120) NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `means_of_answering`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `means_of_answering` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `means_of_answering` (
  `id` SMALLINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `means_of_answering` VARCHAR(60) NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `means_of_answering_UNIQUE` (`means_of_answering` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rating_type`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rating_type` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rating_type` (
  `id` SMALLINT(3) UNSIGNED NOT NULL AUTO_INCREMENT,
  `rating_type` VARCHAR(60) NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `rating_type_UNIQUE` (`rating_type` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `question` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `question` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(300) NOT NULL,
  `question_category` SMALLINT(3) UNSIGNED NOT NULL,
  `means_of_answering` SMALLINT(3) UNSIGNED NOT NULL,
  `rating_type` SMALLINT(3) UNSIGNED NULL,
  `faculty` INT UNSIGNED NULL,
  `department` INT UNSIGNED NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_question_question_category1_idx` (`question_category` ASC),
  INDEX `fk_question_means_of_answering1_idx` (`means_of_answering` ASC),
  INDEX `fk_question_faculty1_idx` (`faculty` ASC),
  INDEX `fk_question_department1_idx` (`department` ASC),
  INDEX `fk_question_rating_type1_idx` (`rating_type` ASC),
  CONSTRAINT `fk_question_question_category1`
    FOREIGN KEY (`question_category`)
    REFERENCES `question_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_means_of_answering1`
    FOREIGN KEY (`means_of_answering`)
    REFERENCES `means_of_answering` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_faculty1`
    FOREIGN KEY (`faculty`)
    REFERENCES `faculty` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_department1`
    FOREIGN KEY (`department`)
    REFERENCES `department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_question_rating_type1`
    FOREIGN KEY (`rating_type`)
    REFERENCES `rating_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `admission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `admission` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `admission` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `admission` VARCHAR(60) NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `admission_UNIQUE` (`admission` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `degree`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `degree` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `degree` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(120) NOT NULL,
  `admission` INT UNSIGNED NOT NULL,
  `faculty` INT UNSIGNED NULL,
  `department` INT UNSIGNED NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_degree_admission1_idx` (`admission` ASC),
  INDEX `fk_degree_faculty1_idx` (`faculty` ASC),
  INDEX `fk_degree_department1_idx` (`department` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC),
  CONSTRAINT `fk_degree_admission1`
    FOREIGN KEY (`admission`)
    REFERENCES `admission` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_degree_faculty1`
    FOREIGN KEY (`faculty`)
    REFERENCES `faculty` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_degree_department1`
    FOREIGN KEY (`department`)
    REFERENCES `department` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `course`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `course` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `course` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(120) NOT NULL,
  `code` VARCHAR(20) NOT NULL,
  `degree` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `title_UNIQUE` (`title` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_course_degree1_idx` (`degree` ASC),
  CONSTRAINT `fk_course_degree1`
    FOREIGN KEY (`degree`)
    REFERENCES `degree` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `rating`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `rating` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `rating` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `rating` VARCHAR(60) NULL,
  `rating_type` SMALLINT(3) UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_rating_rating_type1_idx` (`rating_type` ASC),
  CONSTRAINT `fk_rating_rating_type1`
    FOREIGN KEY (`rating_type`)
    REFERENCES `rating_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `evaluation_session`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `evaluation_session` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `evaluation_session` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `start_date` DATE NULL,
  `end_date` DATE NULL,
  `academic_year` VARCHAR(45) NULL,
  `semester` VARCHAR(45) NULL,
  `admission_year` DATE NOT NULL,
  `degree` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_evaluation_session_degree2_idx` (`degree` ASC),
  CONSTRAINT `fk_evaluation_session_degree2`
    FOREIGN KEY (`degree`)
    REFERENCES `degree` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `course_of_session`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `course_of_session` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `course_of_session` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `course` INT UNSIGNED NOT NULL,
  `faculty_member` INT UNSIGNED NOT NULL,
  `evaluation_session` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_courses_of_session_course1_idx` (`course` ASC),
  INDEX `fk_courses_of_session_faculty_member1_idx` (`faculty_member` ASC),
  INDEX `fk_course_of_session_evaluation_session1_idx` (`evaluation_session` ASC),
  CONSTRAINT `fk_courses_of_session_course1`
    FOREIGN KEY (`course`)
    REFERENCES `course` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_courses_of_session_faculty_member1`
    FOREIGN KEY (`faculty_member`)
    REFERENCES `faculty_member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_of_session_evaluation_session1`
    FOREIGN KEY (`evaluation_session`)
    REFERENCES `evaluation_session` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `evaluated_question`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `evaluated_question` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `evaluated_question` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `question` VARCHAR(300) NOT NULL,
  `means_of_answering` SMALLINT(3) UNSIGNED NOT NULL,
  `question_category` SMALLINT(3) UNSIGNED NOT NULL,
  `rating_type` SMALLINT(3) UNSIGNED NULL,
  `evaluation_session` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_evaluated_question_means_of_answering1_idx` (`means_of_answering` ASC),
  INDEX `fk_evaluated_question_question_category1_idx` (`question_category` ASC),
  INDEX `fk_evaluated_question_rating_type1_idx` (`rating_type` ASC),
  INDEX `fk_evaluated_question_evaluation_session1_idx` (`evaluation_session` ASC),
  CONSTRAINT `fk_evaluated_question_means_of_answering1`
    FOREIGN KEY (`means_of_answering`)
    REFERENCES `means_of_answering` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_question_category1`
    FOREIGN KEY (`question_category`)
    REFERENCES `question_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_rating_type1`
    FOREIGN KEY (`rating_type`)
    REFERENCES `rating_type` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_evaluation_session1`
    FOREIGN KEY (`evaluation_session`)
    REFERENCES `evaluation_session` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `evaluation_instance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `evaluation_instance` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `evaluation_instance` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `anonymous_identity` VARCHAR(45) NOT NULL,
  `evaluation_session` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  PRIMARY KEY (`id`),
  INDEX `fk_evaluation_instance_evaluation_session1_idx` (`evaluation_session` ASC),
  CONSTRAINT `fk_evaluation_instance_evaluation_session1`
    FOREIGN KEY (`evaluation_session`)
    REFERENCES `evaluation_session` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `assessed_evaluation`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `assessed_evaluation` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `assessed_evaluation` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `question_description` VARCHAR(300) NULL,
  `evaluated_question` INT UNSIGNED NOT NULL,
  `question_category` SMALLINT(3) UNSIGNED NOT NULL,
  `rating` VARCHAR(10) NULL,
  `standard_deviation` DOUBLE(5,2) NULL,
  `percentage_score` VARCHAR(20) NULL,
  `evaluation_session` INT UNSIGNED NOT NULL,
  `course_of_session` INT UNSIGNED NOT NULL,
  `version` INT NULL,
  `active` TINYINT(1) NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_assessed_evaluation_evaluated_question1_idx` (`evaluated_question` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_assessed_evaluation_question_category1_idx` (`question_category` ASC),
  INDEX `fk_assessed_evaluation_evaluation_session1_idx` (`evaluation_session` ASC),
  INDEX `fk_assessed_evaluation_course_of_session1_idx` (`course_of_session` ASC),
  CONSTRAINT `fk_assessed_evaluation_evaluated_question1`
    FOREIGN KEY (`evaluated_question`)
    REFERENCES `evaluated_question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assessed_evaluation_question_category1`
    FOREIGN KEY (`question_category`)
    REFERENCES `question_category` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assessed_evaluation_evaluation_session1`
    FOREIGN KEY (`evaluation_session`)
    REFERENCES `evaluation_session` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_assessed_evaluation_course_of_session1`
    FOREIGN KEY (`course_of_session`)
    REFERENCES `course_of_session` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `assessed_evaluation_comment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `assessed_evaluation_comment` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `assessed_evaluation_comment` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `comment` VARCHAR(300) NULL,
  `assessed_evaluation` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_assessed_evaluation_comments_assessed_evaluation1_idx` (`assessed_evaluation` ASC),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_assessed_evaluation_comments_assessed_evaluation1`
    FOREIGN KEY (`assessed_evaluation`)
    REFERENCES `assessed_evaluation` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `course_of_instance`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `course_of_instance` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `course_of_instance` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `course_of_session` INT UNSIGNED NOT NULL,
  `evaluation_instance` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  INDEX `fk_course_of_session_has_evaluation_instance_evaluation_ins_idx` (`evaluation_instance` ASC),
  INDEX `fk_course_of_session_has_evaluation_instance_course_of_sess_idx` (`course_of_session` ASC),
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  CONSTRAINT `fk_course_of_session_has_evaluation_instance_course_of_session1`
    FOREIGN KEY (`course_of_session`)
    REFERENCES `course_of_session` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_course_of_session_has_evaluation_instance_evaluation_insta1`
    FOREIGN KEY (`evaluation_instance`)
    REFERENCES `evaluation_instance` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `evaluated_question_answer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `evaluated_question_answer` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `evaluated_question_answer` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `rating` VARCHAR(20) NULL,
  `reasoning` VARCHAR(300) NULL,
  `comment1` VARCHAR(300) NULL,
  `comment2` VARCHAR(300) NULL,
  `comment3` VARCHAR(300) NULL,
  `evaluated_question` INT UNSIGNED NOT NULL,
  `course_of_instance` INT UNSIGNED NOT NULL,
  `evaluation_instance` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_evaluated_question_course_of_instance1_idx` (`course_of_instance` ASC),
  INDEX `fk_evaluated_question_evaluation_instance1_idx` (`evaluation_instance` ASC),
  INDEX `fk_evaluated_question_answer_evaluated_question1_idx` (`evaluated_question` ASC),
  CONSTRAINT `fk_evaluated_question_course_of_instance1`
    FOREIGN KEY (`course_of_instance`)
    REFERENCES `course_of_instance` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_evaluation_instance1`
    FOREIGN KEY (`evaluation_instance`)
    REFERENCES `evaluation_instance` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_evaluated_question_answer_evaluated_question1`
    FOREIGN KEY (`evaluated_question`)
    REFERENCES `evaluated_question` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `student_feedback`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `student_feedback` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `student_feedback` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `feedback` VARCHAR(400) NOT NULL,
  `faculty_member` INT UNSIGNED NOT NULL,
  `date_completed` DATETIME NULL,
  `evaluation_session` INT UNSIGNED NOT NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_student_feedback_faculty_member1_idx` (`faculty_member` ASC),
  INDEX `fk_student_feedback_evaluation_session1_idx` (`evaluation_session` ASC),
  CONSTRAINT `fk_student_feedback_faculty_member1`
    FOREIGN KEY (`faculty_member`)
    REFERENCES `faculty_member` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_feedback_evaluation_session1`
    FOREIGN KEY (`evaluation_session`)
    REFERENCES `evaluation_session` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SHOW WARNINGS;

-- -----------------------------------------------------
-- Table `overall_admin`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `overall_admin` ;

SHOW WARNINGS;
CREATE TABLE IF NOT EXISTS `overall_admin` (
  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(150) NULL,
  `active` TINYINT(1) NULL,
  `version` INT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;

SHOW WARNINGS;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `faculty_member_role`
-- -----------------------------------------------------
START TRANSACTION;
USE `ocena`;
INSERT INTO `faculty_member_role` (`id`, `faculty_member_role`, `active`, `version`) VALUES (1, 'Management', NULL, NULL);
INSERT INTO `faculty_member_role` (`id`, `faculty_member_role`, `active`, `version`) VALUES (2, 'Lecturer', NULL, NULL);
INSERT INTO `faculty_member_role` (`id`, `faculty_member_role`, `active`, `version`) VALUES (3, 'Student', NULL, NULL);
INSERT INTO `faculty_member_role` (`id`, `faculty_member_role`, `active`, `version`) VALUES (4, 'Other staff', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `user_group`
-- -----------------------------------------------------
START TRANSACTION;
USE `ocena`;
INSERT INTO `user_group` (`id`, `user_group`, `active`, `version`) VALUES (1, 'Management', NULL, NULL);
INSERT INTO `user_group` (`id`, `user_group`, `active`, `version`) VALUES (2, 'Lecturer', NULL, NULL);
INSERT INTO `user_group` (`id`, `user_group`, `active`, `version`) VALUES (3, 'Student', NULL, NULL);
INSERT INTO `user_group` (`id`, `user_group`, `active`, `version`) VALUES (4, 'Other staff', NULL, NULL);
INSERT INTO `user_group` (`id`, `user_group`, `active`, `version`) VALUES (5, 'Admin', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `means_of_answering`
-- -----------------------------------------------------
START TRANSACTION;
USE `ocena`;
INSERT INTO `means_of_answering` (`id`, `means_of_answering`, `active`, `version`) VALUES (1, 'By rating', NULL, NULL);
INSERT INTO `means_of_answering` (`id`, `means_of_answering`, `active`, `version`) VALUES (2, 'By reasoning', NULL, NULL);
INSERT INTO `means_of_answering` (`id`, `means_of_answering`, `active`, `version`) VALUES (3, 'By listing comments', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `rating_type`
-- -----------------------------------------------------
START TRANSACTION;
USE `ocena`;
INSERT INTO `rating_type` (`id`, `rating_type`, `active`, `version`) VALUES (1, 'star', NULL, NULL);
INSERT INTO `rating_type` (`id`, `rating_type`, `active`, `version`) VALUES (2, 'boolean', NULL, NULL);
INSERT INTO `rating_type` (`id`, `rating_type`, `active`, `version`) VALUES (3, 'yes or no', NULL, NULL);
INSERT INTO `rating_type` (`id`, `rating_type`, `active`, `version`) VALUES (4, 'percentage', NULL, NULL);

COMMIT;


-- -----------------------------------------------------
-- Data for table `admission`
-- -----------------------------------------------------
START TRANSACTION;
USE `ocena`;
INSERT INTO `admission` (`id`, `admission`, `active`, `version`) VALUES (1, 'Undergraduate', 1, NULL);
INSERT INTO `admission` (`id`, `admission`, `active`, `version`) VALUES (2, 'Graduate', 1, NULL);

COMMIT;


