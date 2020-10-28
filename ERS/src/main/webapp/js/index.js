$(document).ready(function () {
  loadLogin();
  var currentUser;
  var userRole;
  var t;
  var empListTable;
  var employeesList;
  var reimbID;
  var reimbursementAuthorID;
});

function determineUserRole(roleId) {
  var role = roleId == 2 ? "Manager" : roleId == 1 ? "Associate" : "N/A";

  return role;
}

function loginFunctionality() {
  // on click of the submit button inside of the login modal, validate the
  // users input
  $("#validateUser").click(validateLogin);
  $("#validateNewUser").click(validateNewUser);

  // Get the modals
  var modal = document.getElementById("myModal");
  var signUpModal = document.getElementById("signUpModal");

  // Get the button that opens the modal
  var btn = document.getElementById("login");
  var signUpBtn = document.getElementById("signup");

  // Get the <span> element that closes the modal
  var loginClose = document.getElementsByClassName("close")[0];
  var signUpClose = document.getElementsByClassName("close")[1];

  // When the user clicks the button, open the modal
  btn.onclick = function () {
    clearLoginForm();
    $("#login-message").hide();
    modal.style.display = "flex";
  };

  signUpBtn.onclick = function () {
    clearSignUpForm();
    signUpModal.style.display = "flex";
  };

  // When the user clicks on <span> (x), close the modal
  loginClose.onclick = function () {
    modal.style.display = "none";
  };

  signUpClose.onclick = function () {
    signUpModal.style.display = "none";
  };

  // When the user clicks anywhere outside of the modal, close it
  window.onclick = function (event) {
    if (event.target == modal || event.target == signUpModal) {
      modal.style.display = "none";
      signUpModal.style.display = "none";
    }
  };
}

function loadLogin() {
  // console.log to tell that we are entering this function
  console.log("in loadLogin()");

  // create new Http Request
  let xhr = new XMLHttpRequest();

  // Open the request using a Get method, get the view called login.view and
  // do this asynchronously
  xhr.open("GET", "login.view", true);

  // send the request
  xhr.send();

  // if the request comes back ok, get the element with the id "view" from the
  // dom and insert the response.
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      document.getElementById("view").innerHTML = xhr.responseText;
      loginFunctionality();
    }
  };
}

function validateLogin() {
  // get value from HTML inputs
  var username = $("#username").val();
  var password = $("#password").val();

  // create record called "credentials"
  var credentials = [username, password];
  var credentialsJSON = JSON.stringify(credentials);

  // create XHR request
  var xhr = new XMLHttpRequest();

  // open the xhr request, POST method, declare the route||path and set the
  // asynchronous boolean to true
  xhr.open("POST", "login", true);

  // send the credentials in the XHR request and wait for a response.
  xhr.send(credentialsJSON);

  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      let user = JSON.parse(xhr.responseText);
      if (user) {
        alert("Login successful!");
        window.localStorage.setItem("user", xhr.responseText);
        console.log(user);
        currentUser = user;
        userRole = determineUserRole(currentUser.userRoleID);
        loadReimb();
        console.log(`User id: ${user.employeeID} login successful`);
      } else {
        $("#login-message").show();
        $("#login-message").html("Invalid credentials, please try again.");
        clearLoginForm();
      }
    }
  };
}

function validateNewUser() {
  console.log("Inside the appropriate function");
  // get value from HTML inputs
  var firstname = $("#firstname").val();
  var lastname = $("#lastname").val();
  var email = $("#email").val();
  var username = $("#signUpUsername").val();
  var password = $("#signUpPassword").val();

  // create record called "credentials"
  var credentials = [firstname, lastname, email, username, password];
  var credentialsJSON = JSON.stringify(credentials);

  // create XHR request
  var xhr = new XMLHttpRequest();

  // open the xhr request, POST method, declare the route||path and set the
  // asynchronous boolean to true
  xhr.open("POST", "user", true);

  // send the credentials in the XHR request and wait for a response.
  xhr.send(credentialsJSON);

  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      let user = JSON.parse(xhr.responseText);
      if (user) {
        // Will return the user object if the user creation occurs
        signUpModal.style.display = "none";
        alert("New Employee successfully added!");
      } else {
        // If the user creation does not successfully occur, some thing
        // should be sent from server side detailing what the issue is.
        // $('#login-message').show();
        // $('#login-message').html('Invalid credentials');
      }
    }
  };
}

function clearLoginForm() {
  document.getElementById("username").value = "";
  document.getElementById("password").value = "";
}

function clearSignUpForm() {
  document.getElementById("firstname").value = "";
  document.getElementById("lastname").value = "";
  document.getElementById("email").value = "";
  document.getElementById("signUpUsername").value = "";
  document.getElementById("signUpPassword").value = "";
}

function loadReimb() {
  // console.log to tell that we are entering this function
  console.log("Sucessfully inside of loadReimb()");

  // create new Http Request
  let xhr = new XMLHttpRequest();

  // Open the request using a Get method, get the view called login.view and
  // do this asynchronously
  xhr.open("GET", "reimbursement.view", true);

  // send the request
  xhr.send();

  // if the request comes back ok, get the element with the id "view" from the
  // dom and insert the response.
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      document.getElementById("view").innerHTML = xhr.responseText;
      runReimbFunctionality();
    }
  };
}

function runReimbFunctionality() {
  t = $("#reimbTable").DataTable();

  $("#accept").click(acceptReimb);
  $("#decline").click(declineReimb);

  if (currentUser.userRoleID == 1) {
    $("#accept").hide();
    $("#decline").hide();
  }

  $("#navigate-home").click(loadReimb);
  $("#profile").click(loadProfile);
  $("#logout").click(logoutFunc);
  $("#createReimbBtn").click(loadCreateReimb);
  getInitialRecords(t);

  t.on("click", "tr", function () {
    if ($(this).hasClass("selected")) {
      $(this).removeClass("selected");
    } else {
      t.$("row.selected").removeClass("selected");

      $(this).addClass("selected");
      var row = $(this).closest("tr");
      var rowIndex = row.index();
      console.log("row data: ", row.author);
      // reimbID = t.cell(rowIndex, 0).data();
      reimbID = row[0].cells[0].innerHTML;
      // reimbursementAuthorID= t.cell(rowIndex, 1).data();
      reimbursementAuthorID = row[0].cells[1].innerHTML;

      console.log("Reimbursement ID: :", reimbID);
    }
  });
}

function getInitialRecords(t) {
  // check to see if the current users information was saved in the global
  // variable

  console.log("Current User: ", JSON.stringify(currentUser));
  var userInfo;
  // status id(the last element in userInfo array) determines which reimbursement records to retrieve
  /* 0 = get all the records (total aggregate for managers, only one relateing to the user for non managers)
	   1 = get pending records only 
	   2 = get declined records only
	   3 = get approved records only
	*/
  if (currentUser.userRoleID == 1) {
    userInfo = [currentUser.employeeID, currentUser.userRoleID, 0];
  } else if (currentUser.userRoleID == 2) {
    userInfo = [currentUser.employeeID, currentUser.userRoleID, 0];
  } else {
    console.log("How did you get this far????? HACKS!!");
  }

  // Stringify the user information
  var userInfoJSON = JSON.stringify(userInfo);

  // // create XHR request
  var xhr = new XMLHttpRequest();

  // open the xhr request, POST method, declare the route||path and set the
  // asynchronous boolean to true
  xhr.open("POST", "reimburse", true);

  // // send the credentials in the XHR request and wait for a response.
  xhr.send(userInfoJSON);

  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      let records = JSON.parse(xhr.responseText);
      if (records) {
        //print the amount of reimbursements available
        console.log("length of records: ", records.length);

        //for each of the records, place them into the table
        for (var i = 0; i < records.length; i++) {
          console.log(records[i]);
          //place each column from each record in a variable
          var id = records[i].reimbID;
          var userID = records[i].author;
          var type = records[i].typeID;
          var amount = records[i].amount;
          var description = records[i].description;
          var status = records[i].statusID;

          var statusString;
          var typeString;

          switch (type) {
            case 1:
              typeString = "Lodging";
              break;
            case 2:
              typeString = "Travel";
              break;
            case 3:
              typeString = "Food";
              break;
            case 4:
              typeString = "Other";
              break;
          }

          switch (status) {
            case 1:
              statusString = "Pending";
              break;
            case 2:
              statusString = "Declined";
              break;
            case 3:
              statusString = "Approved";
              break;
          }

          t.row
            .add([id, userID, typeString, amount, description, statusString])
            .draw(false);
        }
      } else {
        console.log("Ooops, something went wrong. Please refactor...");
      }
    }
  };
}

function acceptReimb() {
  if (currentUser.employeeID == reimbursementAuthorID) {
    alert("You are unauthorized to manage this record");
  } else {
    reimbID;
    var userID = currentUser.employeeID;
    var statusID = 3;

    console.log(reimbID, userID, statusID);

    // create JSON record to be sent to Servlet
    var updateRecord = [userID, reimbID, statusID];
    var updateRecordJSON = JSON.stringify(updateRecord);
    console.log(updateRecordJSON);

    // Initiate the XHR call to the servlet

    var xhr = new XMLHttpRequest();

    // open the xhr request, POST method, declare the route||path and set
    // the
    // asynchronous boolean to true
    xhr.open("PUT", "updateReimbursement", true);

    // // send the credentials in the XHR request and wait for a response.
    xhr.send(updateRecordJSON);

    xhr.onreadystatechange = function () {
      if (xhr.readyState == 4 && xhr.status == 200) {
        // call to load the reimbursement view page
        loadReimb();
      }
    };
  }
}

function declineReimb() {
  if (currentUser.employeeID == reimbursementAuthorID) {
    alert("You are unauthorized to manage this record");
  } else {
    reimbID;
    var userID = currentUser.employeeID;
    var statusID = 2;

    console.log(reimbID, userID, statusID);

    // create JSON record to be sent to Servlet
    var updateRecord = [userID, reimbID, statusID];
    var updateRecordJSON = JSON.stringify(updateRecord);
    console.log(updateRecordJSON);

    // Initiate the XHR call to the servlet

    var xhr = new XMLHttpRequest();

    // open the xhr request, POST method, declare the route||path and set
    // the
    // asynchronous boolean to true
    xhr.open("PUT", "updateReimbursement", true);

    // // send the credentials in the XHR request and wait for a response.
    xhr.send(updateRecordJSON);

    xhr.onreadystatechange = function () {
      if (xhr.readyState == 4 && xhr.status == 200) {
        // call to load the reimbursement view page
        loadReimb();
      }
    };
  }
}

function logoutFunc() {
  currentUser = "";
  employeesList = "";
  window.localStorage.clear();
  loadLogin();
}

function loadCreateReimb() {
  // console.log to tell that we are entering this function
  console.log("Sucessfully inside of loadcreateReimb()");

  // create new Http Request
  let xhr = new XMLHttpRequest();

  // Open the request using a Get method, get the view called login.view and
  // do this asynchronously
  xhr.open("GET", "create.view", true);

  // send the request
  xhr.send();

  // if the request comes back ok, get the element with the id "view" from the
  // dom and insert the response.
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      document.getElementById("view").innerHTML = xhr.responseText;
      createReimbFunctionality();
    }
  };
}

function createReimbFunctionality() {
  $("#navigate-home").click(loadReimb);
  $("#profile").click(loadProfile);
  $("#logout").click(logoutFunc);
  $("#addReimbBtn").click(createReimbFunctionalityCont);
}

function createReimbFunctionalityCont() {
  //capture the inputs from the fields
  var date = $("#date").val();
  var description = $("#description").val();
  var amount = $("#amount").val();
  temp = $("#type").val();
  var type;
  switch (temp) {
    case "Lodging":
      type = 1;
      break;
    case "Travel":
      type = 2;
      break;
    case "Food":
      type = 3;
      break;
    case "Other":
      type = 4;
      break;
  }

  //create JSON record to be sent to Servlet
  var newRecord = [amount, currentUser.employeeID, type, description];
  var newRecordJSON = JSON.stringify(newRecord);
  console.log(newRecordJSON);

  //Initiate the XHR call to the servlet

  var xhr = new XMLHttpRequest();

  // open the xhr request, POST method, declare the route||path and set the
  // asynchronous boolean to true
  xhr.open("PUT", "reimburse", true);

  // // send the credentials in the XHR request and wait for a response.
  xhr.send(newRecordJSON);

  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      alert("Thank you for your request, please await a response");
      //call to load the reimbursement view page
      loadReimb();
    }
  };
}

function loadProfile() {
  // console.log to tell that we are entering this function
  console.log("Successfully inside of loadProfile()");

  // create new Http Request
  let xhr = new XMLHttpRequest();

  // Open the request using a Get method, get the view called login.view and
  // do this asynchronously
  xhr.open("GET", "profile.view", true);

  // send the request
  xhr.send();

  // if the request comes back ok, get the element with the id "view" from the
  // dom and insert the response.
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
      document.getElementById("view").innerHTML = xhr.responseText;
      profileFunctionality();
      loadAllEmployees();
    }
  };
}

function loadAllEmployees() {
  // console.log to tell that we are entering this function
  console.log("Successfully inside of loadAllEmployees()");

  // create new Http Request
  let xhr = new XMLHttpRequest();

  // Open the request using a Get method, get the view called login.view and
  // do this asynchronously
  xhr.open("GET", "user", true);

  // send the request
  xhr.send();

  // if the request comes back ok, get the element with the id "view" from the
  // dom and insert the response.
  xhr.onreadystatechange = function () {
    if (xhr.readyState == 4 && xhr.status == 200) {
	  // clear all Employee list information 
	  employeesList = null;
	  empListTable.clear();

	  // populate employee list information 
      employeesList = JSON.parse(xhr.responseText);
      console.log("resulting data: ");
      for( i = 0; i<employeesList.length ; i++ ) {
		if(employeesList[i].employeeID == currentUser.employeeID) {continue;}

        empListTable.row
          .add([
            employeesList[i].first_Name + " " + employeesList[i].last_Name,
            employeesList[i].employeeID,
            determineUserRole(employeesList[i].userRoleID)
          ])
          .draw(false);
      };
    }
  };
}

function profileFunctionality() {
  empListTable = $("#emp-list-table").DataTable({
    columnDefs: [
      {
        targets: -1,
        data: null,
        defaultContent: "<button>Update Role</button>",
      },
    ],
  });

  $("#navigate-home").click(loadReimb);
  $("#profile").click(loadProfile);
  $("#logout").click(logoutFunc);
  $("#addReimbBtn").click(createReimbFunctionalityCont);

  // Check the roles of the current user and adjust what is shown accordingly
  if (currentUser.userRoleID == 1) {
    $("#user-mgnt").hide(); // regular associates should not have the ability to manage other users
  }

  // display the users info within the HTML
  $("#role").html(userRole);
  $("#name").html(currentUser.first_Name + " " + currentUser.last_Name);

  $("#emp-list-table tbody").on("click", "button", function () {
    var row = $(this).closest("tr");
    var empId = row[0].cells[1].innerHTML;

    //udpateRole(empId);

    // console.log to tell that we are entering this function
    console.log("Successfully inside of changeRole()");

    // Update the users Role
    var emp = employeesList.find((emp) => emp.employeeID == empId);
    var roleId = emp.userRoleID == 1 ? 2 : 1; // ternary to toggle between the two role [associate(1) and Manager(2)]

    // create request body
    var body = [empId, roleId];
    var JSONBody = JSON.stringify(body);

    // create new Http Request
    let xhr = new XMLHttpRequest();

    // Open the request using a Get method, get the view called login.view and
    // do this asynchronously
    xhr.open("PUT", "user", true);

    // send the request
    xhr.send(JSONBody);

    // if the request comes back ok, get the element with the id "view" from the
    // dom and insert the response.
    xhr.onreadystatechange = function () {
      if (xhr.readyState == 4 && xhr.status == 200) {
		loadAllEmployees();
      }
    };
    return false;
  });
}

// function updateRole(userId) {}
