var language = "en";

//<editor-fold defaultstate="collapsed" desc="Document">
$(document).ready(function () {

    $.i18n.properties({
        name: "text",
        path: "assets/bundles/",
        mode: "both",
        language: language
    });
    //<editor-fold defaultstate="collapsed" desc="Button Icons">
    $(".backButton").button({
        text: false,
        icons: {
            primary: "ui-icon-arrowthick-1-w"
        }
    });
    $(".fowardButton").button({
        text: false,
        icons: {
            primary: "ui-icon-arrowthick-1-e"
        }
    });
    $(".cancelButton").button({
        text: false,
        icons: {
            primary: "ui-icon-cancel"
        }
    });
    $(".addButton").button({
        text: false,
        icons: {
            primary: "ui-icon-plus"
        }
    });
    $(".editButton").button({
        text: false,
        icons: {
            primary: "ui-icon-pencil"
        }
    });
    $(".deleteButton").button({
        text: false,
        icons: {
            primary: "ui-icon-trash"
        }
    });
    $(".saveButton").button({
        text: false,
        icons: {
            primary: "ui-icon-disk"
        }
    });
    $(".openButton").button({
        text: false,
        icons: {
            primary: "ui-icon-extlink"
        }
    });
    $(".searchButton").button({
        text: false,
        icons: {
            primary: "ui-icon-search"
        }
    });
    $(".openFolderButton").button({
        text: false,
        icons: {
            primary: "ui-icon-folder-open"
        }
    });
    $(".refreshButton").button({
        text: false,
        icons: {
            primary: "ui-icon-refresh"
        }
    });
    $(".downloadButton").button({
        text: false,
        icons: {
            primary: "ui-icon-arrowthick-1-s"
        }
    });
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Button Tooltips">
    $("i").tooltip();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Zebra Stripes">
    //$("tr:odd").addClass("odd");
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tabs">

    $("#selectSecondTabs").tabs({active: 1});
    $("#selectThirdTabs").tabs({active: 2});
    $("#tabs").tabs();
    $("#tabs2").tabs();
    $("#innertabs").tabs();
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Date Pickers">
    $(".datePicker").datepicker({
        dateFormat: "dd-mm-yy",
        changeMonth: true,
        changeYear: true,
        yearRange: "-80:+0" // last eighty years
    });
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Accordion">
    var headers = ["H1", "H2", "H3", "H4", "H5", "H6"];
    $(".accordion").click(function (e) {
        var target = e.target,
                name = target.nodeName.toUpperCase();
        if ($.inArray(name, headers) > -1) {
            var subItem = $(target).next();
            //slideUp all elements (except target) at current depth or greater
            var depth = $(subItem).parents().length;
            var allAtDepth = $(".accordion p, .accordion div").filter(function () {
                if ($(this).parents().length >= depth && this !== subItem.get(0)) {
                    return true;
                }
            });
            $(allAtDepth).slideUp("fast");
            //slideToggle target content and adjust bottom border if necessary
            subItem.slideToggle("fast", function () {
                $(".accordion :visible:last").css("border-radius", "0 0 10px 10px");
            });
            $(target).css({"border-bottom-right-radius": "0", "border-bottom-left-radius": "0"});
        }
    });
    $("#accordion").accordion({
        header: "> div > h3",
        /*event: "click hoverintent",*/
        heightStyle: "content",
        collapsible: true
    })
            .sortable({
                axis: "y",
                handle: "h3",
                stop: function (event, ui) {
                    // IE doesn"t register the blur when sorting
                    // so trigger focusout handlers to remove .ui-state-focus
                    ui.item.children("h3").triggerHandler("focusout");
                }
            });
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="AJAX Setup">
    $(function () {
        //setup ajax error handling
        $.ajaxSetup({
            error: function (x) {
                if (x.status === 403) {
                    showMessage("Error", "Sorry, your session has expired. Please login again to continue");
                    window.location.href = "/sci/index.jsp";
                } else {
                    showMessage("Error ", x.responseText);
                }
            },
            headers: {
                "Accept-Language": language
            }
        });
    });
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Events">

    /*Search*/
    $("input#search").keyup(function (e) {
// Set Timeout
        clearTimeout($.data(this, "timer"));
        // Set Search String
        var search_string = $(this).val();
        // Do Search
        if (search_string === "") {
            $("div#results").fadeOut();
        } else {
            $("div#results").fadeIn();
            $(this).data("timer", setTimeout("search", 100));
        }
    });
    $("input#participantsSearch").keyup(function (e) {
        $("#filteredPersonsList tbody").load("/sci/filterOutPotentialParticipants", {
            filter: $(e.target).val()
        }, function () {
            $(".addButton").button({
                text: false,
                icons: {
                    primary: "ui-icon-plus"
                }
            });
            $(".openButton").button({
                text: false,
                icons: {
                    primary: "ui-icon-extlink"
                }
            });
        });
    });
    $("input#organizationsSearch").keyup(function (e) {
        $("#filteredOrganizationsList tbody").load("/sci/filterOutPotentialOrganizers", {
            filter: $(e.target).val()
        }, function () {
            $(".addButton").button({
                text: false,
                icons: {
                    primary: "ui-icon-plus"
                }
            });
            $(".openButton").button({
                text: false,
                icons: {
                    primary: "ui-icon-extlink"
                }
            });
        });
    });
    $("input#publicMemberTypesSearch").keyup(function (e) {
        $("#filteredPublicMemberTypesList tbody").load("/sci/filterOutPotentialTargetPublicMemberTypes", {
            filter: $(e.target).val()
        }, function () {
            $(".addButton").button({
                text: false,
                icons: {
                    primary: "ui-icon-plus"
                }
            });
            $(".openButton").button({
                text: false,
                icons: {
                    primary: "ui-icon-extlink"
                }
            });
        });
    });
    $("#modulesCovered input[type='checkbox']").change(function () {
        if (this.checked) {
// the checkbox was checked
            addModuleCovered($(this).val());
        } else {
// the checkbox was unchecked
            removeModuleCovered($(this).val());
        }
    });
    $("#professionalCompetencyTable input[type='checkbox']").change(function () {
        if (this.checked) {
// the checkbox was checked
            addPersonsProfessionalCompetency($(this).val());
        } else {
// the checkbox was unchecked
            removePersonsProfessionalCompetency($(this).val());
        }
    });
    $("#afflictedFacilityExcludesGroupRow input[type='checkbox'']").change(function () {
        if (this.checked) {
// the checkbox was checked
            $("#exclusionBasisRow").show();
        } else {
// the checkbox was unchecked
            $("#exclusionBasisRow").hide();
        }
    });
    $("#afflictedFacilityIsReopeningRow input[type='checkbox']").change(function () {
        if (this.checked) {
// the checkbox was checked
            $("#reopeningDateRow").show();
        } else {
// the checkbox was unchecked
            $("#reopeningDateRow").hide();
        }
    });
    $("#workgroupTypesPermissions input[type='checkbox']").change(function () {
        if (this.checked) {
// the checkbox was checked
            addWorkgroupTypeViewPermission($(this).val());
        } else {
// the checkbox was unchecked
            removeWorkgroupTypeViewPermission($(this).val());
        }
    });
    $("#workgroupPermissions input[type='checkbox']").change(function () {
        if (this.checked) {
// the checkbox was checked
            addWorkgroupViewPermission($(this).val());
        } else {
// the checkbox was unchecked
            removeWorkgroupViewPermission($(this).val());
        }
    });
    $("#afflictedFacility").change(function (e) {
        $("#afflictedFacilityType").load("/sci/filterOutFacilityTypes", {
            facility: $(e.target).val()
        });
    });
    $("#incomingMajor").change(function (e) {
        $("#incomingCourse").load("/sci/filterOutCourses", {
            major: $(e.target).val()
        });
    });
    $("#incumbentMajor").change(function (e) {
        $("#incumbentCourse").load("/sci/filterOutCourses", {
            major: $(e.target).val()
        });
    });
    $("#initialAlertSource").change(function (e) {
        var source = $("#initialAlertSource option:selected").text();
        if (source.toLowerCase() === "other") {
            addSelectableSourceOfInitialAlert();
        }
    });
    $("#informantCategory").change(function (e) {
        var category = $("#informantCategory option:selected").text();
        if (category.toLowerCase() === "other") {
            addSelectableInformantCategory();
        }
    });
    $("#physicalEvidenceType").change(function (e) {
        var physicalEvidence = $("#physicalEvidenceType option:selected").text();
        if (physicalEvidence.toLowerCase() === "other") {
            addSelectablePhysicalEvidenceType();
        }
    });
    $("#incidenceLocation").change(function (e) {
        var incidenceLocation = $("#incidenceLocation option:selected").text();
        if (incidenceLocation.toLowerCase() === "other") {
            addSelectableIncidenceLocation();
        }
    });
    $("#caseCategory").change(function (e) {
        $("#caseInstance").load("/sci/filterViolations", {
            violationCategoryId: $(e.target).val(),
            violationTypeId: $("#caseSubcategory").val()
        });
    });
    $("#caseSubcategory").change(function (e) {
        $("#caseInstance").load("/sci/filterViolations", {
            violationTypeId: $(e.target).val(),
            violationCategoryId: $("#caseCategory").val()
        });
    });
    $("#perpetratorCategory").change(function (e) {
        $("#perpetratorSubCategory").load("/sci/filterOutPerpetratorSubcategories", {
            category: $(e.target).val()
        });
    });
    $("#facilitiesConsiderations input[type='checkbox']").change(function () {
        if (this.checked) {
// the checkbox was checked
            addFacilityConsideration($(this).val());
        } else {
// the checkbox was unchecked
            removeFacilityConsideration($(this).val());
        }
    });
    $("#afflictedReligion").change(function (e) {
        var religion = $("#afflictedReligion option:selected").text();
        if (religion.toLowerCase() === "other") {
            addSelectableReligion();
        }
    });
    $("#afflictedCivilianStatus").change(function (e) {
        var status = $("#afflictedCivilianStatus option:selected").text();
        if (status.toLowerCase() === "other") {
            addSelectableCivilianStatus();
        }
    });
    $("#afflictedChildCareArrangement").change(function (e) {
        var arrangement = $("#afflictedChildCareArrangement option:selected").text();
        if (arrangement.toLowerCase() === "other") {
            addSelectableChildCareArrangement();
        }
    });
    $("#violationPurpose").change(function (e) {
        var violationPurpose = $("#violationPurpose option:selected").text();
        if (violationPurpose.toLowerCase() === "other") {
            addSelectableViolationPurpose();
        }
    });
    $("#violationContext").change(function (e) {
        var violationContext = $("#violationContext option:selected").text();
        if (violationContext.toLowerCase() === "other") {
            addSelectableViolationContext();
        }
    });
    $("#violationMechanism").change(function (e) {
        var violationMechanism = $("#violationMechanism option:selected").text();
        if (violationMechanism.toLowerCase() === "other") {
            addSelectableViolationMechanism();
        }
    });
    $("#violationOutcome").change(function (e) {
        var violationOutcome = $("#violationOutcome option:selected").text();
        if (violationOutcome.toLowerCase() === "other") {
            addSelectableViolationOutcome();
        }
    });
    $("#evaluationPrepared input[type='checkbox']").change(function () {
        if (this.checked) {
//The checkbox was checked
//Elevate status to ready
            updateStatus("2");
        }
    });
    $("#evaluationReady input[type='checkbox']").change(function () {
        if (!this.checked) {
//The checkbox was unchecked
//Revert status to in-preparation
            updateStatus("1");
        }
    });
    $("#issueEvaluation input[type='checkbox']").change(function () {
        if (this.checked) {
//The checkbox was checked
//Make the evaluation available to the attendants
            updateStatus("3");
        }
    });
    $("#evaluationCompleted input[type='checkbox']").change(function () {
        if (this.checked) {
//The checkbox was checked
//Make the evaluation as having been completed by the trainees
            updateStatus("4");
        }
    });
    $("#courseType").change(function (e) {



//Hide all course blocks
        $("div.courseBlock").hide();
        //Get the selected course type
        courseType = $(e.target).val();
        //Show the selected course type block
        switch (courseType) {
            case "1"://MULTIPLE_CHOICE_ONE_ANSWER,
                $("div#MULTIPLE_CHOICE_ONE_ANSWER").show();
                break;
            case "2"://MULTIPLE_CHOICE_MULTIPLE_ANSWERS,
                $("div#MULTIPLE_CHOICE_MULTIPLE_ANSWERS").show();
                break;
            case "3"://COMMENT_OR_ESSAY_BOX_SINGLE,
                $("div#COMMENT_OR_ESSAY_BOX_SINGLE").show();
                break;
            case "4"://RANKING,
                $("div#RANKING").show();
                break;
            case "5"://RATING,
                $("div#RATING").show();
                break;
            case "6"://MATRIX_OF_CHOICES_ONE_ANSWER,
                $("div#MATRIX_OF_CHOICES_ONE_ANSWER").show();
                break;
            case "7"://MATRIX_OF_CHOICES_MULTIPLE_ANSWERS,
                $("div#MATRIX_OF_CHOICES_MULTIPLE_ANSWERS").show();
                break;
            case "8"://MATRIX_OF_DROP_DOWN_MENUS,
                $("div#MATRIX_OF_DROP_DOWN_MENUS").show();
                break;
            case "9"://TEXTBOXES_GENERAL,
                $("div#TEXTBOXES_GENERAL").show();
                break;
            case "10"://COMMENT_OR_ESSAY_BOX_MULTIPLE,
                $("div#COMMENT_OR_ESSAY_BOX_MULTIPLE").show();
                break;
            case "11"://TEXTBOXES_NUMERICAL,
                $("div#TEXTBOXES_NUMERICAL").show();
                break;
            case "12"://TEXTBOXES_DATE
                $("div#TEXTBOXES_DATE").show();
                break;
        }
    });
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Animations">
    // Animate buttons, move reflection and fade
    $("#report-tools img").hover(function () {
        $(this).stop().animate({
            marginTop: "-10px"
        }, 200);
        $(this).parent().find("span").stop().animate({
            marginTop: "18px",
            opacity: 0.25
        }, 200);
    }, function () {
        $(this).stop().animate({
            marginTop: "0px"
        }, 300);
        $(this).parent().find("span").stop().animate({
            marginTop: "1px",
            opacity: 1
        }, 300);
    });
    $("#menu-toggle").click(function (e) {
        e.preventDefault();
        $("#wrapper").toggleClass("toggled");
    });
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Date picker">

    //</editor-fold>


});
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Sliding form">
$(function () {
    /*
     number of fieldsets
     */
    var fieldsetCount = $('.sliding-form').children().length;

    /*
     current position of fieldset / navigation link
     */
    var current = 1;

    /*
     sum and save the widths of each one of the fieldsets
     set the final sum as the total width of the steps element
     */
    var stepsWidth = 0;
    var widths = new Array();
    $('#steps .step').each(function (i) {
        var $step = $(this);
        widths[i] = stepsWidth;
        stepsWidth += $step.width();
    });
    $('#steps').width(stepsWidth);

    /*
     to avoid problems in IE, focus the first input of the form
     */
    $('.sliding-form').children(':first').find(':input:first').focus();

    /*
     show the navigation bar
     */
    $('#slider-navigation').show();

    /*
     when clicking on a navigation link 
     the form slides to the corresponding fieldset
     */
    $('#slider-navigation a').bind('click', function (e) {
        var $this = $(this);
        var prev = current;
        $this.closest('ul').find('li').removeClass('selected');
        $this.parent().addClass('selected');
        /*
         we store the position of the link
         in the current variable	
         */
        current = $this.parent().index() + 1;
        /*
         animate / slide to the next or to the corresponding
         fieldset. The order of the links in the navigation
         is the order of the fieldsets.
         Also, after sliding, we trigger the focus on the first 
         input element of the new fieldset
         If we clicked on the last link (confirmation), then we validate
         all the fieldsets, otherwise we validate the previous one
         before the form slided
         */
        $('#steps').stop().animate({
            marginLeft: '-' + widths[current - 1] + 'px'
        }, 500, function () {
            if (current === fieldsetCount)
                validateSteps();
            else
                validateStep(prev);
            $('.sliding-form').children(':nth-child(' + parseInt(current) + ')').find(':input:first').focus();
        });
        e.preventDefault();
    });

    /*
     clicking on the tab (on the last input of each fieldset), makes the form
     slide to the next step
     */
    $('.sliding-form > fieldset').each(function () {
        var $fieldset = $(this);
        $fieldset.children(':last').find(':input').keydown(function (e) {
            if (e.which === 9) {
                $('#slider-navigation li:nth-child(' + (parseInt(current) + 1) + ') a').click();
                /* force the blur for validation */
                $(this).blur();
                e.preventDefault();
            }
        });
    });

    /*
     validates errors on all the fieldsets
     records if the Form has errors in $('.sliding-form').data()
     */
    function validateSteps() {
        var FormErrors = false;
        for (var i = 1; i < fieldsetCount; ++i) {
            var error = validateStep(i);
            if (error === -1)
                FormErrors = true;
        }
        $('.sliding-form').data('errors', FormErrors);
    }

    /*
     validates one fieldset
     and returns -1 if errors found, or 1 if not
     */
    function validateStep(step) {
        if (step === fieldsetCount)
            return;

        var error = 1;
        var hasError = false;

        $('.sliding-form')
                .children(':nth-child(' + parseInt(step) + ')')
                .find(':input:not(button)')
                .each(function () {
                    var $this = $(this);

                    if ($this.is($("#admission-year"))) {
                        $.ajax({
                            url: "/Ocena/checkFacultyMemberRole",
                            type: 'POST',
                            data: "memberRole=" + $("#faculty-member-role").val(),
                            success: function (data) {
                                if (data !== "") {
                                    if ($("#admission-year").val().trim().length === 0) {
                                        hasError = true;

                                        $("#admission-year").css('background-color', '#FFEDEF');
                                        var $link = $('#slider-navigation li:nth-child(' + parseInt(step) + ') a');
                                        $link.parent().find('.error,.checked').remove();

                                        var valclass = 'checked';
                                        if (hasError) {
                                            error = -1;
                                            valclass = 'error';
                                        }
                                        $('<span class="' + valclass + '"></span>').insertAfter($link);

                                        return error;
                                    } else {
                                        $("#admission-year").css('background-color', '#FFFFFF');
                                    }
                                } else {
                                    $("#admission-year").css('background-color', '#FFFFFF');
                                }
                            }
                        });
                    } else if ($this.prop('required')) {
                        if ($this.val().trim().length === 0) {
                            hasError = true;
                            $this.css('background-color', '#FFEDEF');
                        } else {
                            $this.css('background-color', '#FFFFFF');
                        }
                    }
                });

        var $link = $('#slider-navigation li:nth-child(' + parseInt(step) + ') a');
        $link.parent().find('.error,.checked').remove();

        var valclass = 'checked';
        if (hasError) {
            error = -1;
            valclass = 'error';
        }
        $('<span class="' + valclass + '"></span>').insertAfter($link);

        return error;
    }

    /*
     if there are errors don't allow the user to submit
     */
    $('.slider-submit-button').bind('click', function () {
        if ($('.sliding-form').data('errors')) {
            alert('Please correct the errors in the Form');
            return false;
        }
    });
});
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Tables">

function setTableIcons() {

    $(".editButton").button({
        text: false,
        icons: {
            primary: "ui-icon-pencil"
        }
    });
    $(".deleteButton").button({
        text: false,
        icons: {
            primary: "ui-icon-trash"
        }
    });
    $(".openButton").button({
        text: false,
        icons: {
            primary: "ui-icon-extlink"
        }
    });
    $(".downloadButton").button({
        text: false,
        icons: {
            primary: "ui-icon-arrowthick-1-s"
        }
    });
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Forms">
function submitForm(form) {
    $("#" + form).submit();
}

function validateform(form) {
    var flag = true;
    $("form#" + form + "[required]").each(function () {
        if ($(this).val() === null || $(this).val().trim() === "") {
            showMessage("Error", $(this).attr("name") + " is required");
            flag = false;
        }
    });
    return flag;
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Messages">

(function ($) {
    $.fn.flash_message = function (options) {

        options = $.extend({
            text: "Done",
            time: 2000,
            how: "before",
            class_name: ""
        }, options);
        return $(this).each(function () {
            if ($(this).parent().find(".flash_message").get(0))
                return;
            var message = $("<span />", {
                "class": "flash_message " + options.class_name,
                text: options.text
            }).hide().fadeIn("fast");
            $(this)[options.how](message);
            message.delay(options.time).fadeOut("normal", function () {
                $(this).remove();
            });
        });
    };
})(jQuery);
$(".add-item").click(function () {

    $("#status-area").flash_message({
        text: "Added to cart!",
        how: "append"
    });
});
function showMessage(title, message) {

    //Set the message
    $("#message").text(message);
    //Pop the message dialog
    $("#message-dialog").dialog({
        resizable: false,
        height: 160,
        modal: true,
        title: title,
        buttons: {
            "OK": function () {
                $(this).dialog("close");
            }
        }
    });
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Windows">
function loadWindow(target) {
    window.location = target;
}

function loadPreviousWindow() {
    parent.history.back();
    return false;
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Login user">
function loginUser() {

    if ($("#login-username").val().trim() !== "" || $("#login-password").val().trim() !== "") {
        if ($("#login-username").val() !== null || $("#login-password").val() !== null) {

            $.ajax({
                url: "/Ocena/checkLoginInfo",
                type: "POST",
                data: "password=" + $("#login-password").val() + "&username=" + $("#login-username").val(),
                success: function (data) {

                    if (data !== "") {

                        $("#login-form").submit();

                    } else {
                        $("#invalid-login-info").html("<table class=\"table table-responsive table-hover\"><tbody><tr class=\"warning\"><td> <span> Invalid credentials. Contact your administrator </span> </td></tr></tbody></table>");
                    }

                },
                dataType: "HTML"
            });
        } else {
            $("#invalid-login-info").html("<table class=\"table table-responsive table-hover\"><tbody><tr class=\"warning\"><td> <span> Fill the login details or contact your administrator </span> </td></tr></tbody></table>");
        }
    } else {
        $("#invalid-login-info").html("<table class=\"table table-responsive table-hover\"><tbody><tr class=\"warning\"><td> <span> Fill the login details or contact your administrator </span> </td></tr></tbody></table>");
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Institution">

function editInstitution(name, abbreviation, country) {
//Display the initial values
    $("#institution-name").val(name);
    $("#institution-abbreviation").val(abbreviation);
    $("#country").val(country);
    $("#institution-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit institution",
        modal: true,
        resizable: false,
        buttons: {
            "Save": function () {
                //Read in update values
                var name = $("#institution-name").val();
                var abbreviation = $("#institution-abbreviation").val();
                var country = $("#country").val();
                //Ascertain validity of name
                if (name === null || name.trim() === "") {
                    showMessage("Error", "College name is required");
                    return;
                } else {
                    if (name.length > 120) {
                        showMessage("Error", "College name is longer than 120 characters");
                        return;
                    }
                }

                //Ascertain validity of abbreviation
                if (abbreviation === null || abbreviation.trim() === "") {
                    showMessage("Error", "The abbreviation is required");
                    return;
                } else {
                    if (abbreviation.length > 20) {
                        showMessage("Error", "The abbreviation is longer than 20 characters");
                        return;
                    }
                }

                //Ascertain validity of country
                if (country === null) {
                    showMessage("Error", "Kindly select the country");
                    return;
                }

                //Send the values to the application server for updating in the database
                $.ajax({
                    type: "post",
                    url: "/Ocena/editInstitution",
                    data: "name=" + name + "&abbreviation=" + abbreviation + "&country=" + country,
                    success: function (data) {
                        //Update institution table
                        $("#content").html(data);
                        //Clear dialog text fields
                        $("#institution-name").val("");
                        $("#institution-abbreviation").val("");
                        $("#country").val("");
                    },
                    dataType: "html"
                });
                $(this).dialog("close");
            }
        },
        close: function (event, ui) {

        }
    });
}

function removeInstitution(id) {
    $("#message").text("Are you sure you want to remove this institution?");
    //Confirm record removal request
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm request",
        resizable: false,
        modal: true,
        context: $(this),
        buttons: {
            "Yes": function () {
                $.ajax({
                    type: "post",
                    url: "/Ocena/removeInstitution",
                    data: "id=" + id,
                    success: function (data) {

                        //Update institution table
                        $("#content").html(data);
                    },
                    dataType: "html"
                });
                //Close the dialog 
                $(this).dialog("close");
            },
            "No": function () {
                //Close the dialog 
                $(this).dialog("close");
            }

        },
        close: function (event, ui) {

        }
    });
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="College">
function addCollege() {
    $("#college-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Add College",
        resizable: false,
        modal: true,
        buttons: {
            "Add": function () {

                //Retrieve the passed in values
                var institution = $("#institution").val();
                var name = $("#college-name").val();
                var abbreviation = $("#college-abbreviation").val();
                //Ascertain validity of college name
                if (name === null || name.trim() === "") {
                    showMessage("Error", "College name is required");
                    return;
                } else {
                    if (name.length > 300) {
                        showMessage("Error", "College name is longer than 300 characters");
                        return;
                    }
                }

                //Ascertain validity of college name abbreviation
                if (abbreviation === null || abbreviation.trim() === "") {
                    showMessage("Error", "College name abbreviation is null");
                    return;
                } else {
                    if (abbreviation.length > 20) {
                        showMessage("Error", "College name abbreviation is longer than 20 characters");
                        return;
                    }
                }

                //Send the values to the application server for database recording 
                $.ajax({
                    type: "post",
                    url: "/Ocena/addCollege",
                    data: "name=" + name + "&abbreviation=" + abbreviation + "&institution=" + institution,
                    success: function (data) {
                        //Update college table
                        $("table#college-table tbody").html(data);
                        //Clear the dialog text fields
                        $("#college-name").val("");
                        $("#college-name").focus();
                        $("#college-abbreviation").val("");
                    },
                    dataType: "html"
                });
            }

        },
        close: function (event, ui) {
        }
    });
}

function editCollege(id, name, abbreviation) {
//Display the initial values
    $("#college-name").val(name);
    $("#college-abbreviation").val(abbreviation);
    $("#college-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit college",
        modal: true,
        resizable: false,
        buttons: {
            "Save": function () {
                //Read in update values
                var name = $("#college-name").val();
                var abbreviation = $("#college-abbreviation").val();
                var institution = $("#institution").val();
                //Ascertain validity of name
                if (name === null || name.trim() === "") {
                    showMessage("Error", "College name is required");
                    return;
                } else {
                    if (name.length > 300) {
                        showMessage("Error", "College name is longer than 300 characters");
                        return;
                    }
                }

                //Ascertain validity of abbreviation
                if (abbreviation === null || abbreviation.trim() === "") {
                    showMessage("Error", "The abbreviation is required");
                    return;
                } else {
                    if (abbreviation.length > 20) {
                        showMessage("Error", "The abbreviation is longer than 20 characters");
                        return;
                    }
                }

                //Send the values to the application server for updating in the database
                $.ajax({
                    type: "post",
                    url: "/Ocena/editCollege",
                    data: "id=" + id + "&name=" + name + "&abbreviation=" + abbreviation,
                    success: function (data) {
                        //Update college table
                        $("table#college-table tbody").html(data);
                        //Clear dialog text fields
                        $("#college-name").val("");
                        $("#college-abbreviation").val("");
                    },
                    dataType: "html"
                });
                $(this).dialog("close");
            }
        },
        close: function (event, ui) {

        }
    });
}

function removeCollege(id) {
    $("#message").text("Are you sure you want to remove this college?");
    //Confirm record removal request
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm request",
        resizable: false,
        modal: true,
        context: $(this),
        buttons: {
            "Yes": function () {
                $.ajax({
                    type: "post",
                    url: "/Ocena/removeCollege",
                    data: "id=" + id,
                    success: function (data) {

                        //Update college table
                        $("table#college-table tbody").html(data);
                    },
                    dataType: "html"
                });
                //Close the dialog 
                $(this).dialog("close");
            },
            "No": function () {
                //Close the dialog 
                $(this).dialog("close");
            }

        },
        close: function (event, ui) {

        }
    });
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Faculty">

function addFaculty(collegeId, collegeName) {

    $("#faculty-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Add a faculty in the " + collegeName,
        resizable: false,
        modal: true,
        buttons: {
            "Add": function () {
                //Read in update values
                var name = $("#faculty-name").val();
                var abbreviation = $("#faculty-abbreviation").val();
                //Read in email contact values
                var email = $("#faculty-email").val();
                //Read in phone contact details
                var mobileNumber = $("#faculty-mobile-number").val();
                var fixedNumber = $("#faculty-fixed-number").val();
                //Read in postal contact details
                var boxNumber = $("#faculty-box-number").val();
                var postalCode = $("#faculty-postal-code").val();
                var town = $("#town").val();
                var country = $("#country").val();
                //Ascertain validity of faculty name
                if (name === null || name.trim() === "") {
                    showMessage("Error", "Faculty name is required");
                    return;
                } else {
                    if (name.length > 300) {
                        showMessage("Error", "Faculty name is longer than 300 characters");
                        return;
                    }
                }

                //Ascertain validity of faculty name abbreviation
                if (abbreviation === null || abbreviation.trim() === "") {
                    showMessage("Error", "Faculty name abbreviation is null");
                    return;
                } else {
                    if (abbreviation.length > 20) {
                        showMessage("Error", "Faculty name abbreviation is longer than 20 characters");
                        return;
                    }
                }

                //<editor-fold defaultstate="collapsed" desc="contacts check">
                /*
                 //Ascertain validity of email
                 if (email === null || email.trim() === "") {
                 showMessage("Error", "Email address is required");
                 return;
                 } else {
                 if (email.length > 65) {
                 showMessage("Error", "Email address is longer than 65 characters");
                 return;
                 }
                 }
                 */

                //Ascertain validity of mobile number
//                if (mobileNumber === null) {
//                    if (mobileNumber.length > 20) {
//                        showMessage("Error", "Mobile number is longer than 20 characters");
//                        return;
//                    }
//                }
//
//                //Ascertain validity of fixed number
//                if (fixedNumber === null) {
//                    if (fixedNumber.length > 20) {
//                        showMessage("Error", "Fixed number is longer than 20 characters");
//                        return;
//                    }
//                }
//
//                //Ascertain validity of box number
//                if (boxNumber === null) {
//                    if (boxNumber.length > 20) {
//                        showMessage("Error", "Box number is longer than 20 characters");
//                        return;
//                    }
//                }
//
//                //Ascertain validity of postal code
//                if (postalCode === null) {
//                    if (postalCode.length > 20) {
//                        showMessage("Error", "Postal code is longer than 20 characters");
//                        return;
//                    }
//                }
//
//                //Ascertain validity of town
//                if (town === null) {
//                    if (town.length > 100) {
//                        showMessage("Error", "Town name is longer than 30 characters");
//                        return;
//                    }
//                }
//
//                //Ascertain validity of country
//                if (country === null) {
//                    showMessage("Error", "Kindly select the country");
//                    return;
//                }
                //</editor-fold>

                //Send the values to the application server for updating in the database
                $.ajax({
                    type: "post",
                    url: "/Ocena/addFaculty",
                    data: "name=" + name + "&abbreviation=" + abbreviation + "&collegeId=" + collegeId +
                            "&email=" + email + "&mobileNumber=" + mobileNumber + "&fixedNumber=" + fixedNumber +
                            "&boxNumber=" + boxNumber + "&postalCode=" + postalCode + "&town=" + town + "&country=" + country,
                    success: function (data) {

                        $("table#faculty-table tbody").html(data);
                        $("#faculty-name").val("");
                        $("#faculty-name").focus();
                        $("#faculty-abbreviation").val("");
                        $("#faculty-email").val("");
                        $("#faculty-mobile-number").val("");
                        $("#faculty-fixed-number").val("");
                        $("#faculty-box-number").val("");
                    },
                    dataType: "html"
                });
            }
        },
        close: function (event, ui) {

        }
    });
}

function removeFaculty(id, college) {
    $("#message").text("Are you sure you want to remove this faculty?");
    //Confirm record removal request
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm request",
        resizable: false,
        modal: true,
        context: $(this),
        buttons: {
            "Yes": function () {
                $.ajax({
                    type: "post",
                    url: "/Ocena/removeFaculty",
                    data: "id=" + id + "&collegeId=" + college,
                    success: function (data) {

                        //Update faculty table
                        $("table#faculty-table tbody").html(data);
                    },
                    dataType: "html"
                });
                //Close the dialog 
                $(this).dialog("close");
            },
            "No": function () {
                //Close the dialog 
                $(this).dialog("close");
            }

        },
        close: function (event, ui) {

        }
    });
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Department">

function addDepartment(facultyId, facultyName, collegeId) {

    $("#department-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Add a department in " + facultyName,
        resizable: false,
        modal: true,
        buttons: {
            "Add": function () {
                //Read in update values
                var name = $("#department-name").val();
                var abbreviation = $("#department-abbreviation").val();
                //Read in email contact values
                var email = $("#department-email").val();
                //Read in phone contact details
                var mobileNumber = $("#department-mobile-number").val();
                var fixedNumber = $("#department-fixed-number").val();
                //Read in postal contact details
                var boxNumber = $("#department-box-number").val();
                var postalCode = $("#department-postal-code").val();
                var town = $("#town").val();
                var country = $("#country").val();
                //Ascertain validity of department name
                if (name === null || name.trim() === "") {
                    showMessage("Error", "Department name is required");
                    return;
                } else {
                    if (name.length > 300) {
                        showMessage("Error", "Department name is longer than 300 characters");
                        return;
                    }
                }

                //Ascertain validity of department name abbreviation
                if (abbreviation === null || abbreviation.trim() === "") {
                    showMessage("Error", "Department name abbreviation is null");
                    return;
                } else {
                    if (abbreviation.length > 20) {
                        showMessage("Error", "Department name abbreviation is longer than 20 characters");
                        return;
                    }
                }

                //<editor-fold defaultstate="collapsed" desc="contacts check">

                /*  //Ascertain validity of email
                 if (email === null || email.trim() === "") {
                 showMessage("Error", "Email address is required");
                 return;
                 } else {
                 if (email.length > 65) {
                 showMessage("Error", "Email address is longer than 65 characters");
                 return;
                 }
                 }
                 
                 */
                //Ascertain validity of mobile number
                //                if (mobileNumber === null) {
                //                    if (mobileNumber.length > 20) {
                //                        showMessage("Error", "Mobile number is longer than 20 characters");
                //                        return;
                //                    }
                //                }
                //
                //                //Ascertain validity of fixed number
                //                if (fixedNumber === null) {
                //                    if (fixedNumber.length > 20) {
                //                        showMessage("Error", "Fixed number is longer than 20 characters");
                //                        return;
                //                    }
                //                }
                //
                //                //Ascertain validity of box number
                //                if (boxNumber === null) {
                //                    if (boxNumber.length > 20) {
                //                        showMessage("Error", "Box number is longer than 20 characters");
                //                        return;
                //                    }
                //                }
                //
                //                //Ascertain validity of postal code
                //                if (postalCode === null) {
                //                    if (postalCode.length > 20) {
                //                        showMessage("Error", "Postal code is longer than 20 characters");
                //                        return;
                //                    }
                //                }
                //
                //                //Ascertain validity of town
                //                if (town === null) {
                //                    if (town.length > 100) {
                //                        showMessage("Error", "Town name is longer than 100 characters");
                //                        return;
                //                    }
                //                }
                //
                //                //Ascertain validity of country
                //                if (country === null) {
                //                    showMessage("Error", "Kindly select the country");
                //                    return;
                //                }
                //</editor-fold>

                //Send the values to the application server for updating in the database
                $.ajax({
                    type: "post",
                    url: "/Ocena/addDepartment",
                    data: "name=" + name + "&abbreviation=" + abbreviation + "&facultyId=" + facultyId +
                            "&email=" + email + "&mobileNumber=" + mobileNumber + "&fixedNumber=" + fixedNumber +
                            "&boxNumber=" + boxNumber + "&postalCode=" + postalCode + "&town=" + town + "&country=" + country,
                    success: function () {
                        //clear text boxes
                        $("#department-name").val("");
                        $("#department-name").focus();
                        $("#department-abbreviation").val("");
                        $("#department-email").val("");
                        $("#department-mobile-number").val("");
                        $("#department-fixed-number").val("");
                        $("#department-box-number").val("");
                    },
                    dataType: "html"
                });
                //loadWindow("/Ocena/checkDepartment?facultyId=" + facultyId + "&collegeId=" + collegeId);
                // Reload the page 
                window.setTimeout("location.reload()", 3000); //Reloads after three seconds

            }
        },
        close: function (ui, event) {
            loadWindow("/Ocena/checkDepartment?facultyId=" + facultyId + "&collegeId=" + collegeId);
        }
    });
}
/*
 
 $("table#department-table tbody").html(data);
 
 $("#department-name").val("");
 $("#department-name").focus();
 $("#department-abbreviation").val("");
 $("#department-email").val("");
 $("#department-mobile-number").val("");
 $("#department-fixed-number").val("");
 $("#department-box-number").val("");
 
 },
 dataType: "html"
 });
 }
 },
 close: function (event, ui) {
 
 }
 });
 }
 */
function removeDepartment(id, faculty) {
    $("#message").text("Are you sure you want to remove this department?");
    //Confirm record removal request
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm request",
        resizable: false,
        modal: true,
        context: $(this),
        buttons: {
            "Yes": function () {
                $.ajax({
                    type: "post",
                    url: "/Ocena/removeDepartment",
                    data: "id=" + id + "&facultyId=" + faculty,
                    success: function (data) {

                        //Update department table
                        $("table#department-table tbody").html(data);
                    },
                    dataType: "html"
                });
                // Reload the page 
                window.setTimeout("location.reload()", 3000); //Reloads after three seconds

                //Close the dialog 
                $(this).dialog("close");
            },
            "No": function () {
                //Close the dialog 
                $(this).dialog("close");
            }

        },
        close: function (ui, event) {

        }
    });
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Means of answering">
function addMeansOfAnswering() {

    $("#means-of-answering-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Define a means of answering",
        resizable: false,
        modal: true,
        buttons: {
            "Add": function () {
                //Read value from textbox
                var meansOfAnswering = $("#means-of-answering").val();
                //Ascertain validity of the means of answering
                if (meansOfAnswering === null || meansOfAnswering.trim() === "") {
                    showMessage("Error", "The means of answering is null");
                    return;
                } else {
                    if (meansOfAnswering.length > 60) {
                        showMessage("Error", "The means of answering is longer than 60 characters");
                        return;
                    }
                }

                //Send the means to the application server for record creation
                $.ajax({
                    type: "POST",
                    url: "/Ocena/addMeansOfAnswering",
                    data: "meansOfAnswering=" + meansOfAnswering,
                    success: function (data) {
                        $("table#means-of-answering-table tbody").html(data);
                        $("#means-of-answering").val("");
                    },
                    dataType: "HTML"
                });
            }
        },
        close: function (ui, event) {

        }
    });
}

function editMeansOfAnswering(meansOfAnsweringId, meansOfAnswering) {
//Predisplay the means of answering
    $("#means-of-answering").val(meansOfAnswering);
    $("#means-of-answering-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit this means of answering",
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                //Read value from textbox
                var meansOfAnswering = $("#means-of-answering").val();
                //Ascertain validity of the means of answering
                if (meansOfAnswering === null || meansOfAnswering.trim() === "") {
                    showMessage("Error", "The means of answering is null");
                    return;
                } else {
                    if (meansOfAnswering.length > 60) {
                        showMessage("Error", "The means of answering is longer than 60 characters");
                        return;
                    }
                }

                //Send the means to the application server for record creation
                $.ajax({
                    type: "POST",
                    url: "/Ocena/editMeansOfAnswering",
                    data: "meansOfAnsweringId=" + meansOfAnsweringId + "&meansOfAnswering=" + meansOfAnswering,
                    success: function (data) {
                        $("table#means-of-answering-table tbody").html(data);
                        //Clear the textbox
                        $("#means-of-answering").val("");
                    },
                    dataType: "HTML"
                });
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}

function removeMeansOfAnswering(meansOfAnsweringId) {
//Ask user to confirm record deletion request
    $("#message").text("Are you sure you want to remove this means of answering?");
    //Display the appropriate dialog
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm removal request",
        resizable: false,
        modal: true,
        buttons: {
            //If user confirms
            "Yes": function () {
                $.ajax({
                    type: "POST",
                    url: "/Ocena/removeMeansOfAnswering",
                    data: "meansOfAnsweringId=" + meansOfAnsweringId,
                    success: function (data) {
                        //Update table body
                        $("table#means-of-answering-table tbody").html(data);
                    }
                });
                //Close the dialog
                $(this).dialog("close");
            },
            //If user recedes the request
            "No": function () {
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}

function checkMeansOfAnswering() {
    if ($("#evaluation-question-means-of-answering option:selected").text().contains("rating")) {
        $("#rating-type-hidden").css("display", "table-row");
    } else {
        $("#rating-type-hidden").css("display", "none");
    }
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Rating value">
function addRatingValue() {

    $("#rating-value-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Add a rating value",
        resizable: false,
        modal: true,
        buttons: {
            "Add": function () {
                //Read values from textbox and select
                var ratingValue = $("#rating-value").val();
                var ratingType = $("#rating-type").val();
                //remove % sign if any from the rating value
                ratingValue = ratingValue.replace(/%/g, '');
                //Ascertain validity of the rating value
                if (ratingValue === null || ratingValue.trim() === "") {
                    showMessage("Error", "The rating value is null");
                    return;
                } else {
                    if (ratingValue.length > 60) {
                        showMessage("Error", "The rating value is longer than 60 characters");
                        return;
                    }
                }

                //Ascertain validity of the rating type
                if (ratingType === null || ratingType.trim() === "") {
                    showMessage("Error", "The rating type is null");
                    return;
                } else {
                    try {
                        ratingType = parseInt(ratingType);
                    } catch (e) {
                        showMessage("Error", "The rating type is in an invalid format");
                        return;
                    }
                }

                //Send the rating value to the application server for record creation
                $.ajax({
                    type: "POST",
                    url: "/Ocena/addRating",
                    data: "ratingValue=" + ratingValue + "&ratingType=" + ratingType,
                    success: function (data) {
                        $("table#rating-value-table tbody").html(data);
                        $("#rating-value").val("");
                        $("#rating-type").val("");
                    },
                    dataType: "HTML"
                });
            }
        },
        close: function (ui, event) {

        }
    });
}

function editRatingValue(ratingId, ratingValue, ratingType) {
//Predisplay the rating value
    $("#rating-value").val(ratingValue);
    $("#rating-type").val(ratingType);
    $("#rating-value-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit this rating value",
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                //Read value from textbox and select
                var ratingValue = $("#rating-value").val();
                var ratingType = $("#rating-type").val();
                //remove % sign if any from the rating value
                ratingValue = ratingValue.replace(/%/g, '');
                //Ascertain validity of the rating value
                if (ratingValue === null || ratingValue.trim() === "") {
                    showMessage("Error", "The rating value is null");
                    return;
                } else {
                    if (ratingValue.length > 60) {
                        showMessage("Error", "The rating value is longer than 60 characters");
                        return;
                    }
                }

                //Ascertain validity of the rating type
                if (ratingType === null || ratingType.trim() === "") {
                    showMessage("Error", "The rating type is null");
                    return;
                } else {
                    try {
                        ratingType = parseInt(ratingType);
                    } catch (e) {
                        showMessage("Error", "The rating type is in an invalid format");
                        return;
                    }
                }

                //Send the rating value to the application server for record creation
                $.ajax({
                    type: "POST",
                    url: "/Ocena/editRating",
                    data: "ratingId=" + ratingId + "&ratingValue=" + ratingValue + "&ratingType=" + ratingType,
                    success: function (data) {
                        $("table#rating-value-table tbody").html(data);
                        $("#rating-value").val("");
                        $("#rating-type").val("");
                    },
                    dataType: "HTML"
                });
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}

function removeRatingValue(ratingId) {
//Ask user to confirm record deletion request
    $("#message").text("Are you sure you want to remove this rating value?");
    //Display the appropriate dialog
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm removal request",
        resizable: false,
        modal: true,
        buttons: {
            //If user confirms
            "Yes": function () {
                $.ajax({
                    type: "POST",
                    url: "/Ocena/removeRating",
                    data: "ratingId=" + ratingId,
                    success: function (data) {
                        //Update table body
                        $("table#rating-value-table tbody").html(data);
                    }
                });
                //Close the dialog
                $(this).dialog("close");
            },
            //If user recedes the request
            "No": function () {
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Question category">
function addQuestionCategory() {

    $("#question-category-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Define a question category",
        resizable: false,
        modal: true,
        buttons: {
            "Add": function () {
                //Read value from textbox   
                var questionCategory = $("#question-category").val();
                //Ascertain validity of the question category
                if (questionCategory === null || questionCategory.trim() === "") {
                    showMessage("Error", "The question category is null");
                    return;
                } else {
                    if (questionCategory.length > 120) {
                        showMessage("Error", "The question category is longer than 120 characters");
                        return;
                    }
                }

                //Send the means to the application server for record creation
                $.ajax({
                    type: "POST",
                    url: "/Ocena/addQuestionCategory",
                    data: "questionCategory=" + questionCategory,
                    success: function (data) {
                        $("table#question-category-table tbody").html(data);
                        $("#question-category").val("");
                    },
                    dataType: "HTML"
                });
            }
        },
        close: function (ui, event) {

        }
    });
}

function editQuestionCategory(questionCategoryId, questionCategory) {
//Predisplay the question category
    $("#question-category").val(questionCategory);
    $("#question-category-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit this question category",
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                //Read value from textbox
                var questionCategory = $("#question-category").val();
                //Ascertain validity of the question category
                if (questionCategory === null || questionCategory.trim() === "") {
                    showMessage("Error", "The question category is null");
                    return;
                } else {
                    if (questionCategory.length > 120) {
                        showMessage("Error", "The question category is longer than 120 characters");
                        return;
                    }
                }

                //Send the means to the application server for record creation
                $.ajax({
                    type: "POST",
                    url: "/Ocena/editQuestionCategory",
                    data: "questionCategoryId=" + questionCategoryId + "&questionCategory=" + questionCategory,
                    success: function (data) {
                        $("table#question-category-table").html(data);
                        $("#question-category").val("");
                    },
                    dataType: "HTML"
                });
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}

function removeQuestionCategory(questionCategoryId) {
//Ask user to confirm record deletion request
    $("#message").text("Are you sure you want to remove this question category?");
    //Display the appropriate dialog
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm removal request",
        resizable: false,
        modal: true,
        buttons: {
            //If user confirms
            "Yes": function () {
                $.ajax({
                    type: "POST",
                    url: "/Ocena/removeQuestionCategory",
                    data: "questionCategoryId=" + questionCategoryId,
                    success: function (data) {
                        //Update table body
                        $("table#question-category-table tbody").html(data);
                    },
                    dataType: "HTML"
                });
                //Close the dialog
                $(this).dialog("close");
            },
            //If user recedes the request
            "No": function () {
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evaluation question">
function addQuestion(institutionId, institutionName) {

//Clear the textbox and selects
    $("#evaluation-question").val("");
    $("#evaluation-question-category").val("");
    $("#evaluation-question-rating-type").val("");
    $("#evaluation-question-means-of-answering").val("");
    $("#evaluation-question-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Add an evaluation question",
        modal: true,
        resizable: false,
        buttons: {
            "Add": function () {

                //Read in values from the dialog box
                var question = $("#evaluation-question").val();
                var questionCategory = $("#evaluation-question-category").val();
                var questionMeansOfAnswering = $("#evaluation-question-means-of-answering").val();
                var questionRatingType = $("#evaluation-question-rating-type").val();
                //Determine whether the question belongs to a department or a faculty
                var departmentId, facultId;
                if (institutionName === "faculty") {
                    facultId = institutionId;
                } else if (institutionName === "department") {
                    departmentId = institutionId;
                }

                //Ascertain validity of the evaluation question
                if (question === null || question.trim() === "") {
                    showMessage("Error", "Evaluation question is null");
                    return;
                } else {
                    if (question.length > 200) {
                        showMessage("Error", "The evaluation question is longer than 200 characters");
                    }
                }

                //Ascertain validity of the question category
                if (questionCategory === null || questionCategory.trim() === "") {
                    showMessage("Error", "The question category is null");
                    return;
                } else {
                    try {
                        questionCategory = parseInt(questionCategory);
                    } catch (e) {
                        showMessage("Error", "The question category is in an invalid format");
                        return;
                    }
                }

                //Ascertain validity of the question category
                if (questionMeansOfAnswering === null || questionMeansOfAnswering.trim() === "") {
                    showMessage("Error", "The means of answering is null");
                    return;
                } else {
                    try {
                        questionMeansOfAnswering = parseInt(questionMeansOfAnswering);
                    } catch (e) {
                        showMessage("Error", "The means of answering is in an invalid format");
                        return;
                    }
                }

                //Send the details to the application server for recording
                $.ajax({
                    type: "POST",
                    url: "/Ocena/addQuestion",
                    data: "question=" + question +
                            "&facultyId=" + facultId +
                            "&departmentId=" + departmentId +
                            "&ratingTypeId=" + questionRatingType +
                            "&questionCategoryId=" + questionCategory +
                            "&meansOfAnsweringId=" + questionMeansOfAnswering,
                    success: function (data) {

                        //Update the question table body
                        $("table#evaluation-question-table tbody").html(data);
                        //Clear the textbox and 
                        $("#evaluation-question").val("");
                    },
                    dataType: "HTML"
                });
            }
        },
        close: function (ui, event) {
            //Clear the textbox and selects
            $("#evaluation-question").val("");
            $("#evaluation-question-category").val("");
            $("#evaluation-question-rating-type").val("");
            $("#evaluation-question-means-of-answering").val("");
        }
    });
}

function editQuestion(question, questionId, questionCategory, questionMeansOfAnswering, questionRatingType, facultyId, departmentId) {

//Display the evaluation question details to be edited
    $("#evaluation-question").val(question);
    $("#evaluation-question-category").val(questionCategory);
    $("#evaluation-question-rating-type").val(questionRatingType);
    $("#evaluation-question-means-of-answering").val(questionMeansOfAnswering);
    $("#evaluation-question-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit this evaluation question",
        modal: true,
        resizable: false,
        buttons: {
            "Save": function () {

                //Read in values from the dialog box
                question = $("#evaluation-question").val();
                questionCategory = $("#evaluation-question-category").val();
                questionMeansOfAnswering = $("#evaluation-question-means-of-answering").val();
                questionRatingType = $("#evaluation-question-rating-type").val();
                //Ascertain validity of the evaluation question
                if (question === null || question.trim() === "") {
                    showMessage("Error", "Evaluation question is null");
                    return;
                } else {
                    if (question.length > 200) {
                        showMessage("Error", "The evaluation question is longer than 200 characters");
                    }
                }

                //Ascertain validity of the question category
                if (questionCategory === null || questionCategory.trim() === "") {
                    showMessage("Error", "The question category is null");
                    return;
                } else {
                    try {
                        questionCategory = parseInt(questionCategory);
                    } catch (e) {
                        showMessage("Error", "The question category is in an invalid format");
                        return;
                    }
                }

                //Ascertain validity of the question category
                if (questionMeansOfAnswering === null || questionMeansOfAnswering.trim() === "") {
                    showMessage("Error", "The means of answering is null");
                    return;
                } else {
                    try {
                        questionMeansOfAnswering = parseInt(questionMeansOfAnswering);
                    } catch (e) {
                        showMessage("Error", "The means of answering is in an invalid format");
                        return;
                    }
                }

                //Send the details to the application server for updating
                $.ajax({
                    type: "POST",
                    url: "/Ocena/editQuestion",
                    data: "question=" + question +
                            "&facultyId=" + facultyId +
                            "&questionId=" + questionId +
                            "&departmentId=" + departmentId +
                            "&ratingTypeId=" + questionRatingType +
                            "&questionCategoryId=" + questionCategory +
                            "&meansOfAnsweringId=" + questionMeansOfAnswering,
                    success: function (data) {

                        //Update the question table body
                        $("table#evaluation-question-table tbody").html(data);
                        //Clear the textbox and selects
                        $("#evaluation-question").val("");
                        $("#evaluation-question-category").val("");
                        $("#evaluation-question-rating-type").val("");
                        $("#evaluation-question-means-of-answering").val("");
                    },
                    dataType: "HTML"
                });
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {
            //Clear the textbox and selects
            $("#evaluation-question").val("");
            $("#evaluation-question-category").val("");
            $("#evaluation-question-means-of-answering").val("");
            $("#evaluation-question-rating-type").val("");
        }
    });
}

function removeQuestion(questionId, facultyId, departmentId) {

//Ask user to confirm evaluation question record removal
    $("#message").text("Are you sure you want to remove this evaluation question?");
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm request",
        modal: true,
        resizable: false,
        buttons: {
            "Yes": function () {
                $.ajax({
                    type: "POST",
                    url: "/Ocena/removeQuestion",
                    data: "questionId=" + questionId + "&facultyId=" + facultyId + "&departmentId=" + departmentId,
                    success: function (data) {

                        //Update question table body
                        $("table#evaluation-question-table tbody").html(data);
                    },
                    dataType: "HTML"
                });
                //Close the dialog
                $(this).dialog("close");
            },
            //If user recedes the request
            "No": function () {
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Admission">
function addAdmission() {

    $("#admission-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Define a admission",
        resizable: false,
        modal: true,
        buttons: {
            "Add": function () {
                //Read value from textbox
                var admission = $("#admission").val();
                //Ascertain validity of the admission
                if (admission === null || admission.trim() === "") {
                    showMessage("Error", "The admission is null");
                    return;
                } else {
                    if (admission.length > 60) {
                        showMessage("Error", "The admission is longer than 60 characters");
                        return;
                    }
                }

                //Send the means to the application server for record creation
                $.ajax({
                    type: "POST",
                    url: "/Ocena/addAdmission",
                    data: "admission=" + admission,
                    success: function (data) {
                        $("table#admission-table tbody").html(data);
                        $("#admission").val("");
                    },
                    dataType: "HTML"
                });
            }
        },
        close: function (ui, event) {

        }
    });
}

function editAdmission(admissionId, admission) {
//Predisplay the admission
    $("#admission").val(admission);
    $("#admission-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit this admission",
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {
                //Read value from textbox
                var admission = $("#admission").val();
                //Ascertain validity of the admission
                if (admission === null || admission.trim() === "") {
                    showMessage("Error", "The admission is null");
                    return;
                } else {
                    if (admission.length > 60) {
                        showMessage("Error", "The admission is longer than 60 characters");
                        return;
                    }
                }

                //Send the means to the application server for record creation
                $.ajax({
                    type: "POST",
                    url: "/Ocena/editAdmission",
                    data: "admissionId=" + admissionId + "&admission=" + admission,
                    success: function (data) {
                        $("table#admission-table tbody").html(data);
                        $("#admission").val("");
                    },
                    dataType: "HTML"
                });
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}

function removeAdmission(admissionId) {
//Ask user to confirm record deletion request
    $("#message").text("Are you sure you want to remove this admission?");
    //Display the appropriate dialog
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm removal request",
        resizable: false,
        modal: true,
        buttons: {
            //If user confirms
            "Yes": function () {
                $.ajax({
                    type: "POST",
                    url: "/Ocena/removeAdmission",
                    data: "admissionId=" + admissionId,
                    success: function (data) {
                        //Update table body
                        $("table#admission-table tbody").html(data);
                    }
                });
                //Close the dialog
                $(this).dialog("close");
            },
            //If user recedes the request
            "No": function () {
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Degree">
function addDegree(institutionId, institutionName) {

//Clear the textbox and selects
    $("#degree-name").val("");
    $("#degree-admission").val("");
    $("#degree-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Add a degree",
        modal: true,
        resizable: false,
        buttons: {
            "Add": function () {

                //Read in values from the dialog box
                var degreeName = $("#degree-name").val();
                var admission = $("#degree-admission").val();
                //Determine whether the degree belongs to a department or a faculty
                var departmentId, facultId;
                if (institutionName === "faculty") {
                    facultId = institutionId;
                } else if (institutionName === "department") {
                    departmentId = institutionId;
                }

                //Ascertain validity of the degree
                if (degreeName === null || degreeName.trim() === "") {
                    showMessage("Error", "Degree is null");
                    return;
                } else {
                    if (degreeName.length > 120) {
                        showMessage("Error", "The degree is longer than 120 characters");
                    }
                }

                //Ascertain validity of the degree category
                if (admission === null || admission.trim() === "") {
                    showMessage("Error", "The admission is null");
                    return;
                } else {
                    try {
                        admission = parseInt(admission);
                    } catch (e) {
                        showMessage("Error", "The admission is in an invalid format");
                        return;
                    }
                }

                //Send the details to the application server for recording
                $.ajax({
                    type: "POST",
                    url: "/Ocena/addDegree",
                    data: "degreeName=" + degreeName +
                            "&facultyId=" + facultId +
                            "&departmentId=" + departmentId +
                            "&admissionId=" + admission,
                    success: function (data) {

                        //Update the degree table body
                        $("table#degree-table tbody").html(data);
                        //Clear the textbox and 
                        $("#degree-name").val("");
                        $("#degree-admission").val("");
                    },
                    dataType: "HTML"
                });
            }
        },
        close: function (ui, event) {
            //Clear the textbox and selects
            $("#degree-name").val("");
            $("#degree-admission").val("");
        }
    });
}

function editDegree(degreeName, degreeId, admission, facultyId, departmentId) {

//Display the degree details to be edited
    $("#degree-name").val(degreeName);
    $("#degree-admission").val(admission);
    $("#degree-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit this degree",
        modal: true,
        resizable: false,
        buttons: {
            "Save": function () {

                //Read in values from the dialog box
                degreeName = $("#degree-name").val();
                admission = $("#degree-admission").val();
                //Ascertain validity of the degree
                if (degreeName === null || degreeName.trim() === "") {
                    showMessage("Error", "The degree is null");
                    return;
                } else {
                    if (degreeName.length > 120) {
                        showMessage("Error", "The degree is longer than 120 characters");
                    }
                }

                //Ascertain validity of the degree category
                if (admission === null || admission.trim() === "") {
                    showMessage("Error", "The admission is null");
                    return;
                } else {
                    try {
                        admission = parseInt(admission);
                    } catch (e) {
                        showMessage("Error", "The degree admssion is in an invalid format");
                        return;
                    }
                }

                //Send the details to the application server for updating
                $.ajax({
                    type: "POST",
                    url: "/Ocena/editDegree",
                    data: "degreeName=" + degreeName +
                            "&facultyId=" + facultyId +
                            "&degreeId=" + degreeId +
                            "&departmentId=" + departmentId +
                            "&admissionId=" + admission,
                    success: function (data) {

                        //Update the degree table body
                        $("table#degree-table tbody").html(data);
                        //Clear the textbox and selects
                        $("#degree-name").val("");
                        $("#degree-admission").val("");
                    },
                    dataType: "HTML"
                });
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {
            //Clear the textbox and selects
            $("#degree-name").val("");
            $("#degree-admission").val("");
        }
    });
}

function removeDegree(degreeId, facultyId, departmentId) {

//Ask user to confirm degree record removal
    $("#message").text("Are you sure you want to remove this degree?");
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm request",
        modal: true,
        resizable: false,
        buttons: {
            "Yes": function () {
                $.ajax({
                    type: "POST",
                    url: "/Ocena/removeDegree",
                    data: "degreeId=" + degreeId + "&facultyId=" + facultyId + "&departmentId=" + departmentId,
                    success: function (data) {

                        //Update degree table body
                        $("table#degree-table tbody").html(data);
                    },
                    dataType: "HTML"
                });
                //Close the dialog
                $(this).dialog("close");
            },
            //If user recedes the request
            "No": function () {
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Course">
function addCourse(degreeId) {

//Clear the textbox and selects
    $("#course-title").val("");
    $("#course-code").val("");
    $("#course-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Add a course",
        modal: true,
        resizable: false,
        buttons: {
            "Add": function () {

                //Read in values from the dialog box
                var courseTitle = $("#course-title").val();
                var courseCode = $("#course-code").val();
                //Ascertain validity of the course
                if (courseTitle === null || courseTitle.trim() === "") {
                    showMessage("Error", "The course title is null");
                    return;
                } else {
                    if (courseTitle.length > 120) {
                        showMessage("Error", "The course title is longer than 120 characters");
                    }
                }

                //Ascertain validity of the course code
                if (courseCode === null || courseCode.trim() === "") {
                    showMessage("Error", "The course code is null");
                    return;
                } else {
                    if (courseCode.length > 20) {
                        showMessage("Error", "The course code is longer than 20 characters");
                    }
                }

                //Send the details to the application server for recording
                $.ajax({
                    type: "POST",
                    url: "/Ocena/addCourse",
                    data: "courseTitle=" + courseTitle +
                            "&degreeId=" + degreeId +
                            "&courseCode=" + courseCode,
                    success: function (data) {

                        //Update the course table body
                        $("table#course-table tbody").html(data);
                        //Clear the textbox and 
                        $("#course-title").val("");
                        $("#course-code").val("");
                    },
                    dataType: "HTML"
                });
            }
        },
        close: function (ui, event) {
            //Clear the textbox and selects
            $("#course-title").val("");
            $("#course-code").val("");
        }
    });
}

function editCourse(courseId, courseTitle, courseCode, degreeId) {

//Display the course details to be edited
    $("#course-title").val(courseTitle);
    $("#course-code").val(courseCode);
    $("#course-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit this course",
        modal: true,
        resizable: false,
        buttons: {
            "Save": function () {

                //Read in values from the dialog box
                var courseTitle = $("#course-title").val();
                var courseCode = $("#course-code").val();
                //Ascertain validity of the course
                if (courseTitle === null || courseTitle.trim() === "") {
                    showMessage("Error", "The course title is null");
                    return;
                } else {
                    if (courseTitle.length > 120) {
                        showMessage("Error", "The course title is longer than 120 characters");
                    }
                }

                //Ascertain validity of the course code
                if (courseCode === null || courseCode.trim() === "") {
                    showMessage("Error", "The course code is null");
                    return;
                } else {
                    if (courseCode.length > 20) {
                        showMessage("Error", "The course code is longer than 20 characters");
                    }
                }

                //Send the details to the application server for updating
                $.ajax({
                    type: "POST",
                    url: "/Ocena/editCourse",
                    data: "courseTitle=" + courseTitle +
                            "&degreeId=" + degreeId +
                            "&courseId=" + courseId +
                            "&courseCode=" + courseCode,
                    success: function (data) {

                        //Update the course table body
                        $("table#course-table tbody").html(data);
                        //Clear the textbox and 
                        $("#course-title").val("");
                        $("#course-code").val("");
                    },
                    dataType: "HTML"
                });
                //Close the dialoge
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {
            //Clear the textbox and selects
            $("#course-title").val("");
            $("#course-code").val("");
        }
    });
}

function removeCourse(courseId, degreeId) {

//Ask user to confirm course record removal
    $("#message").text("Are you sure you want to remove this course?");
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm request",
        modal: true,
        resizable: false,
        buttons: {
            "Yes": function () {
                $.ajax({
                    type: "POST",
                    url: "/Ocena/removeCourse",
                    data: "courseId=" + courseId + "&degreeId=" + degreeId,
                    success: function (data) {

                        //Update course table body
                        $("table#course-table tbody").html(data);
                    },
                    dataType: "HTML"
                });
                //Close the dialog
                $(this).dialog("close");
            },
            //If user recedes the request
            "No": function () {
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Update selects">
function updateDegrees(facultyId, departmentId) {
    $.ajax({
        type: "POST",
        url: "/Ocena/updateDegrees",
        data: "admissionId=" + $("#admission-dropdown").val() + "&facultyId=" + facultyId + "&departmentId=" + departmentId,
        success: function (data) {
            $("#degree-dropdown").html(data);
        },
        dataType: "HTML"
    });
}

function updateCourses() {
    $.ajax({
        type: "POST",
        url: "/Ocena/updateCourses",
        data: "degreeId=" + $("#degree-dropdown").val(),
        success: function (data) {
            $("#course-dropdown").html(data);
        },
        dataType: "HTML"
    });
}

function updateFaculties() {
    $.ajax({
        type: "POST",
        url: "/Ocena/updateFaculties",
        data: "collegeId=" + $("#campus-college").val(),
        success: function (data) {
            $("p#campus-faculty-holder").html(data);
        },
        dataType: "HTML"
    });
}

function updateDepartments() {
    $.ajax({
        type: "POST",
        url: "/Ocena/updateDepartments",
        data: "facultyId=" + $("#campus-faculty").val(),
        success: function (data) {
            $("p#campus-department-holder").html(data);
        },
        dataType: "HTML"
    });
}

function updateEditFaculties() {
    $.ajax({
        type: "POST",
        url: "/Ocena/updateEditFaculties",
        data: "collegeId=" + $("#edit-campus-college").val(),
        success: function (data) {
            $("tr#edit-campus-faculty-department-holder").append(data);
        },
        dataType: "HTML"
    });
}

function updateEditDepartments() {
    $.ajax({
        type: "POST",
        url: "/Ocena/updateEditDepartments",
        data: "facultyId=" + $("#edit-campus-faculty").val(),
        success: function (data) {
            $("tr#edit-campus-faculty-department-holder").append(data);
        },
        dataType: "HTML"
    });
}

function updateAdminFaculties() {
    $.ajax({
        type: "POST",
        url: "/Ocena/updateAdminFaculties",
        data: "collegeId=" + $("#edit-admin-campus-college").val(),
        success: function (data) {
            $("tr#edit-admin-campus-faculty-department-holder").append(data);
        },
        dataType: "HTML"
    });
}

function updateAdminDepartments() {
    $.ajax({
        type: "POST",
        url: "/Ocena/updateAdminDepartments",
        data: "facultyId=" + $("#edit-admin-campus-faculty").val(),
        success: function (data) {
            $("tr#edit-admin-campus-faculty-department-holder").append(data);
        },
        dataType: "HTML"
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Evaluation session">

function addEvaluationSession() {
    $.ajax({
        type: "POST",
        url: "/Ocena/setupEvaluationSession",
        data: "facultyId=" + $("#add-facultyId").val() + "&departmentId=" + $("#add-departmentId").val() + "&semester=" + $("#add-semester").val() + "&academicYear="
                + $("#add-academic-year").val() + "&startDate=" + $("#add-start-date").val() + "&endDate=" + $("#add-end-date").val() + "&degreeId="
                + $("#add-course-of-session-degree").val() + "&admissionYear=" + $("#add-admission-month-year").val(),
        success: function () {
            loadWindow("/Ocena/viewEvaluationSessions?facultyId=" + $("#facultyId").val() + "&departmentId=" + $("#departmentId").val());
        },
        dataType: "HTML"
    });
}

function checkDate() {

    //Read in the start date and end date
    var startDate = $("#evaluation-session-start-date").val();
    var endDate = $("#evaluation-session-end-date").val();
    
    //Warn the user if end date comes before start date
    if (startDate > endDate) {
        $("#information-box").attr("hidden", false);
        $("#information-box").text("Invalid end date!");
    } else {
        $("#information-box").attr("hidden", true);
    }

    return;
}

function checkAddDate() {

//Read in the start date and end date
    var startDate = $("#add-start-date").val();
    var endDate = $("#add-end-date").val();
    //Warn the user if end date comes before start date
    if (startDate > endDate) {
        $("#add-information-box").attr("hidden", false);
        $("#add-information-box").text("Invalid end date!");
    } else {
        $("#add-information-box").attr("hidden", true);
    }

    return;
}

function editEvaluationSession(degreeId, startDate, endDate, academicYear, semester, admissionYear, evaluationSessionId) {

//Display the course details to be edited
    $("#evaluation-session-degree").val(degreeId);
    $("#evaluation-session-end-date").val(endDate);
    $("#evaluation-session-semester").val(semester);
    $("#evaluation-session-start-date").val(startDate);
    $("#evaluation-session-academic-year").val(academicYear);
    $("#evaluation-session-admission-year").val(admissionYear);

    $("#evaluation-session-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Edit this evaluation session",
        modal: true,
        resizable: false,
        buttons: {
            "Save": function () {

                //Read in values from the dialog box
                degreeId = $("#evaluation-session-degree").val();
                endDate = $("#evaluation-session-end-date").val();
                semester = $("#evaluation-session-semester").val();
                startDate = $("#evaluation-session-start-date").val();
                academicYear = $("#evaluation-session-academic-year").val();
                admissionYear = $("#evaluation-session-admission-year").val();

                //Ascertain validity of the degree unique identifier
                if (degreeId === null) {
                    showMessage("Error", "Kindly select the degree");
                    return;
                }

                //Ascertain validity of the end date
                if (startDate === null || startDate.trim() === "") {
                    showMessage("Error", "The start date is required");
                    return;
                }

                //Ascertain validity of the end date
                if (endDate === null || endDate.trim() === "") {
                    showMessage("Error", "The end date is required");
                    return;
                }

                //Ascertain validity of the academic
                if (academicYear === null || academicYear.trim() === "") {
                    showMessage("Error", "The academic year is required");
                    return;
                } else {
                    if (academicYear.length > 20) {
                        showMessage("Error", "The academic year is longer than permissible 45 characters");
                    }
                }

                //Ascertain validity of the semester
                if (semester === null || semester.trim() === "") {
                    showMessage("Error", "The end date is required");
                    return;
                } else {
                    if (semester.length > 20) {
                        showMessage("Error", "The semester is longer than permissible 45 characters");
                    }
                }

                //Ascertain validity of the semester
                if (semester === null || semester.trim() === "") {
                    showMessage("Error", "The admission year and month are required");
                    return;
                }

                //Send the details to the application server for updating
                $.ajax({
                    type: "POST",
                    url: "/Ocena/editEvaluationSession",
                    data: "degreeId=" + degreeId +
                            "&endDate=" + endDate +
                            "&semester=" + semester +
                            "&startDate=" + startDate +
                            "&academicYear=" + academicYear +
                            "&admissionYear=" + admissionYear +
                            "&evaluationSessionId=" + evaluationSessionId,
                    success: function () {

                        //Clear the textbox and selects
                        $("#evaluation-session-degree").val("");
                        $("#evaluation-session-end-date").val("");
                        $("#evaluation-session-semester").val("");
                        $("#evaluation-session-start-date").val("");
                        $("#evaluation-session-academic-year").val("");
                        $("#evaluation-session-admission-year").val("");

                        //Load view evaluation sessions window
                        loadWindow("/Ocena/viewEvaluationSessions?facultyId=" + $("#facultyId").val() + "&departmentId=" + $("#departmentId").val());
                    },
                    dataType: "HTML"
                });
                //Close the dialoge
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

            //Clear the textbox and selects
            $("#evaluation-session-degree").val("");
            $("#evaluation-session-end-date").val("");
            $("#evaluation-session-semester").val("");
            $("#evaluation-session-start-date").val("");
            $("#evaluation-session-academic-year").val("");
            $("#evaluation-session-admission-year").val("");
        }
    });
}

function closeEvaluationSession(evaluationSessionId) {

    //Ask user to confirm course record removal
    $("#message").text("Are you sure you want to close this evaluation session?");
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm request",
        modal: true,
        resizable: false,
        buttons: {
            "Yes": function () {
                $.ajax({
                    type: "POST",
                    url: "/Ocena/closeEvaluationSession",
                    data: "evaluationSessionId=" + evaluationSessionId + "&facultyId=" + $("#facultyId").val()
                            + "&departmentId=" + $("#departmentId").val(),
                    success: function (data) {
                        loadWindow("/Ocena/viewEvaluationSessions?facultyId=" + $("#facultyId").val() + "&departmentId=" + $("#departmentId").val());
                    },
                    dataType: "HTML"
                });
                //Close the dialog
                $(this).dialog("close");
            },
            //If user recedes the request
            "No": function () {
                //Close the dialog
                $(this).dialog("close");
            }
        },
        close: function (ui, event) {

        }
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Perform evaluation">
function displayEvaluationQuestions(facultyId, departmentId) {
    $.ajax({
        type: "POST",
        url: "/Ocena/retrieveEvaluationQuestions",
        data: "facultyId=" + facultyId + "&departmentId=" + departmentId,
        success: function (data) {
            //Display evaluation questions
            $("#evaluation-questions-holder").html(data);
        },
        dataType: "HTML"
    });
}

function setCourse() {
    $.ajax({
        type: "POST",
        url: "/Ocena/setCourse",
        data: "courseId=" + $("#course-dropdown").val()
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="User">
function retrieveUser() {
    $.ajax({
        type: "POST",
        url: "/Ocena/retrieveUser",
        data: "referenceNumber=" + $("#upgrade-user-number").val(),
        success: function (data) {
            //Display evaluation questions
            $("#upgrade-user-details-holder").html(data);
        },
        dataType: "HTML"
    });
}

function upgradeUser() {
    $.ajax({
        type: "POST",
        url: "/Ocena/upgradeUser",
        data: "personId=" + $("#upgrade-person-identifier").val() + "&memberRole=" + $("#upgrade-member-role").val() + "&userGroup=" + $("#upgrade-user-group").val(),
        success: function (data) {
            //Display evaluation questions
            $("#upgrade-user-details-holder").html(data);
        },
        dataType: "HTML"
    });
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Course of session">
function addCourseOfSession() {

    $("#course-of-session-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Add course of session",
        resizable: false,
        modal: true,
        buttons: {
            "Add": function () {

                //Retrieve the passed in values
                var course = $("#course-of-session-course").val();
                var facultyMember = $("#course-of-session-lecturer").val();
                var evaluationSession = $("#course-of-session-evaluation-session").val();
                //Ensure a course is selected
                if (course === "") {
                    showMessage("Error!", "Select a course");
                    return;
                }

                //Ensure a faculty member is selected
                if (facultyMember === "") {
                    showMessage("Error!", "Select a faculty member");
                    return;
                }

                //Send the values to the application server for database recording 
                $.ajax({
                    type: "post",
                    url: "/Ocena/addCourseOfSession",
                    data: "courseId=" + course + "&facultyMemberId=" + facultyMember + "&evaluationSessionId=" + evaluationSession,
                    success: function (data) {
                        //Update course of session table
                        $("table#course-of-session-table tbody").html(data);
                        //Clear the dialog text fields
                        $("#course-of-session-course").val("");
                        $("#course-of-session-lecturer").val("");
                    },
                    dataType: "html"
                });
            }

        },
        close: function (event, ui) {
        }
    });
}

function editCourseOfSession(id, facultyMember, course) {
//Display the initial values
    $("#course-of-session-course").val(course);
    $("#course-of-session-lecturer").val(facultyMember);
    $("#course-of-session-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Add course of session",
        resizable: false,
        modal: true,
        buttons: {
            "Save": function () {

                //Retrieve the passed in values
                var course = $("#course-of-session-course").val();
                var facultyMember = $("#course-of-session-lecturer").val();
                var evaluationSession = $("#course-of-session-evaluation-session").val();
                //Ensure a course is selected
                if (course === "") {
                    showMessage("Error!", "Select a course");
                    return;
                }

                //Ensure a faculty member is selected
                if (facultyMember === "") {
                    showMessage("Error!", "Select a faculty member");
                    return;
                }

                //Send the values to the application server for database recording 
                $.ajax({
                    type: "post",
                    url: "/Ocena/editCourseOfSession",
                    data: "courseOfSessionId=" + id + "&courseId=" + course + "&facultyMemberId=" + facultyMember + "&evaluationSessionId=" + evaluationSession,
                    success: function (data) {
                        //Update course of session table
                        $("table#course-of-session-table tbody").html(data);
                        //Clear the dialog text fields
                        $("#course-of-session-course").val("");
                        $("#course-of-session-lecturer").val("");
                    },
                    dataType: "html"
                });
                //Close the dialog box
                $(this).dialog("close");
            }
        },
        close: function (event, ui) {

        }
    });
}

function removeCourseOfSession(id) {
    $("#message").text("Are you sure you want to remove this course of session?");
    //Confirm record removal request
    $("#message-dialog").dialog({
        width: 495,
        height: "auto",
        title: "Confirm request",
        resizable: false,
        modal: true,
        context: $(this),
        buttons: {
            "Yes": function () {
                $.ajax({
                    type: "post",
                    url: "/Ocena/removeCourseOfSession",
                    data: "courseOfSessionId=" + id,
                    success: function (data) {

                        //Update course of session table
                        $("table#course-of-session-table tbody").html(data);
                    },
                    dataType: "html"
                });
                //Close the dialog 
                $(this).dialog("close");
            },
            "No": function () {
                //Close the dialog 
                $(this).dialog("close");
            }

        },
        close: function (event, ui) {

        }
    });
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Password check">
function validatePassword() {
    if ($("#edit-old-password").val() === "unset") {
        return;
    }
    $.ajax({
        url: "/Ocena/validatePassword",
        type: "POST",
        data: "password=" + $("#edit-old-password").val() + "&personId=" + $("#edit-person-id").val(),
        success: function (data) {
            $("#valid-password-information").html(data);
        }
    });
}

function matchPassword() {

    var password = $("#edit-new-password").val();
    var confirmationPassword = $("#edit-confirm-password").val();
    if (password !== confirmationPassword) {
        $("#matching-password-information").html("<span class=\"btn btn-warning\">Passwords do not match!</span>");
    } else {
        $("#matching-password-information").html("");
    }
}

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Overall admin">
function validateOverallAdminPassword() {
    if ($("#overall-admin-old-password").val() === "unset") {
        return;
    }
    $.ajax({
        url: "/Ocena/validateOverallAdminPassword",
        type: "POST",
        data: "password=" + $("#old-overall-admin-password").val(),
        success: function (data) {
            $("#valid-overall-admin-password-information").html(data);
        }
    });
}

function matchOverallAdminPassword() {

    var password = $("#new-overall-admin-password").val();
    var confirmationPassword = $("#confirm-overall-admin-password").val();
    if (password !== confirmationPassword) {
        $("#matching-overall-admin-password-information").html("<span class=\"btn btn-warning\">Passwords do not match!</span>");
    } else {
        $("#matching-overall-admin-password-information").html("");
    }
}

function editAdminCredentials() {
    $("#edit-overall-admin-div").css("display", "block");
}
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Faculty member role">
function displayAdmissionYearHolder() {

    $.ajax({
        url: "/Ocena/checkFacultyMemberRole",
        type: 'POST',
        data: "memberRole=" + $("#faculty-member-role").val(),
        success: function (data) {
            if (data !== "") {
                $("p#admission-year-holder").show();
            } else {
                $("#admission-year").val("");
                $("p#admission-year-holder").hide();
            }
        }
    });
}

//</editor-fold>
