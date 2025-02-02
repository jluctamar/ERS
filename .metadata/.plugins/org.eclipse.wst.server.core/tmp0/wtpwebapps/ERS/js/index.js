$(document).ready(function() {
	loadLogin();
	var currentUser;
	var t;
	var reimbID;
	var reimbursementAuthorID;
});

function loginFunctionality() {
	console.log("Successfully inside of loginFunctionality ()")
	// on click of the submit button inside of the login modal, validate the
	// users input
	$("#validateUser").click(validateLogin);

	// Get the modal
	var modal = document.getElementById('myModal');

	// Get the button that opens the modal
	var btn = document.getElementById("login");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("close")[0];

	// When the user clicks the button, open the modal
	btn.onclick = function() {
		modal.style.display = "block";
	}

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	}
}

function loadLogin() {
	// console.log to tell that we are entering this function
	console.log('in loadLogin()');

	// create new Http Request
	let xhr = new XMLHttpRequest();

	// Open the request using a Get method, get the view called login.view and
	// do this asynchronously
	xhr.open('GET', 'login.view', true);

	// send the request
	xhr.send();

	// if the request comes back ok, get the element with the id "view" from the
	// dom and insert the response.
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById('view').innerHTML = xhr.responseText;
			loginFunctionality();
		}
	}
}

function validateLogin() {
	// get value from HTML inputs
	var username = $("#username").val();
	var password = $("#password").val();

	// create record called "credentials"
	var credentials = [ username, password ];
	var credentialsJSON = JSON.stringify(credentials);

	// create XHR request
	var xhr = new XMLHttpRequest();

	// open the xhr request, POST method, declare the route||path and set the
	// asynchronous boolean to true
	xhr.open('POST', 'login', true);

	// send the credentials in the XHR request and wait for a response.
	xhr.send(credentialsJSON);

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			let user = JSON.parse(xhr.responseText);
			if (user) {
				alert('Login successful!');
				window.localStorage.setItem('user', xhr.responseText);
				console.log(user);
				currentUser = user;
				loadReimb();
				console.log(`User id: ${user.employeeID} login successful`);
			} else {
				$('#login-message').show();
				$('#login-message').html('Invalid credentials');
			}
		}
	}
}

function loadReimb() {
	// console.log to tell that we are entering this function
	console.log('Sucessfully inside of loadReimb()');

	// create new Http Request
	let xhr = new XMLHttpRequest();

	// Open the request using a Get method, get the view called login.view and
	// do this asynchronously
	xhr.open('GET', 'reimbursement.view', true);

	// send the request
	xhr.send();

	// if the request comes back ok, get the element with the id "view" from the
	// dom and insert the response.
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById('view').innerHTML = xhr.responseText;
			runReimbFunctionality();
		}
	}
}

function runReimbFunctionality() {
	
	t = $('#reimbTable').DataTable();

	$("#accept").click(acceptReimb);
	$("#decline").click(declineReimb);
	
	console.log(currentUser.userRoleID);
	if(currentUser.userRoleID == 1){
		$("#accept").hide();
		$("#decline").hide();
	}

	$("#logout").click(logoutFunc);
	$("#createReimb").click(loadCreateReimb);
	getInitialRecords(t);
//	$(t).on( 'click', 'tbody td:not(:first-child)', function (e) {
//        editor.inline( this );
//    } );
	
		t.on('click', 'tr', function() {

        if ($(this).hasClass('selected')) {

            $(this).removeClass('selected');

        } else {

            t.$('row.selected').removeClass('selected');

            $(this).addClass('selected');
            var row = $(this).closest("tr");
            var rowIndex = row.index();
            console.log(rowIndex);
            reimbID = t.cell(rowIndex, 0).data();
            
            console.log(reimbID)
            
            var row2 = $(this).closest("tr");
            var rowIndex2 = row.index();
            console.log(rowIndex2);
            reimbursementAuthorID= t.cell(rowIndex, 1).data();
        }
        
    });

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
		var updateRecord = [ userID, reimbID, statusID ];
		var updateRecordJSON = JSON.stringify(updateRecord);
		console.log(updateRecordJSON);

		// Initiate the XHR call to the servlet

		var xhr = new XMLHttpRequest();

		// open the xhr request, POST method, declare the route||path and set
		// the
		// asynchronous boolean to true
		xhr.open('PUT', 'updateReimbursement', true);

		// // send the credentials in the XHR request and wait for a response.
		xhr.send(updateRecordJSON);

		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				// call to load the reimbursement view page
				loadReimb();
			}

		}
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
		var updateRecord = [ userID, reimbID, statusID ];
		var updateRecordJSON = JSON.stringify(updateRecord);
		console.log(updateRecordJSON);

		// Initiate the XHR call to the servlet

		var xhr = new XMLHttpRequest();

		// open the xhr request, POST method, declare the route||path and set
		// the
		// asynchronous boolean to true
		xhr.open('PUT', 'updateReimbursement', true);

		// // send the credentials in the XHR request and wait for a response.
		xhr.send(updateRecordJSON);

		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				// call to load the reimbursement view page
				loadReimb();
			}

		}
	}
}


function logoutFunc(){
	currentUser = "";
	loadLogin();
}


function getInitialRecords(t) {
	// check to see if the current users information was saved in the global
	// variable
	
	console.log(currentUser);
	var userInfo;
	// if user is an employee or manager set the status id to zero, else if the user is a

	if (currentUser.userRoleID == 1) {

		userInfo = [ currentUser.employeeID, currentUser.userRoleID, 0 ];

	} else if (currentUser.userRoleID == 2) {

		userInfo = [ currentUser.employeeID, currentUser.userRoleID, 0 ];
	} else {
		console.log("How did you get this far????? HACKS!!");
	}

	// Stringify the user information
	var userInfoJSON = JSON.stringify(userInfo);

	// // create XHR request
	var xhr = new XMLHttpRequest();

	// open the xhr request, POST method, declare the route||path and set the
	// asynchronous boolean to true
	xhr.open('POST', 'reimburse', true);

	// // send the credentials in the XHR request and wait for a response.
	xhr.send(userInfoJSON);

	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			let records = JSON.parse(xhr.responseText);
			if (records) {
				
				//print the amount of reimbursements available
				console.log(records.length);
				
				//for each of the records, place them into the table
				for (var i = 0 ; i < records.length; i++){
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
					
					switch(type){
					case 1:
						typeString = "Lodging"
							break;
					case 2:
						typeString = "Travel";
							break;
					case 3: 
						typeString = "Food";
							break;
					case 4:
						typeString = "Other"
							break;
					};
					
					
					switch(status){
					case 1:statusString = "Pending";
					break;
					case 2: statusString ="Declined";
					break;
					case 3: statusString = "Approved";
					break;
					}
					
					
					t.row.add([
						id,
						userID,
						typeString,
						amount,
						description,
						statusString
					]).draw(false);
				};
			} else {
				console.log("Ooops, something went wrong. Please refactor...")
			}
		}
	}
}

function loadCreateReimb() {
	// console.log to tell that we are entering this function
	console.log('Sucessfully inside of loadcreateReimb()');

	// create new Http Request
	let xhr = new XMLHttpRequest();

	// Open the request using a Get method, get the view called login.view and
	// do this asynchronously
	xhr.open('GET','create.view',true);

	// send the request
	xhr.send();

	// if the request comes back ok, get the element with the id "view" from the
	// dom and insert the response.
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			document.getElementById('view').innerHTML = xhr.responseText;
			createReimbFunctionality();
		}
	}
}

function createReimbFunctionality(){

    $("#add").click(createReimbFunctionalityCont);
   
    $("#logout").click(logoutFunc);
}

function createReimbFunctionalityCont(){

    //capture the inputs from the fields   
	var date = $("#date").val();
    var description = $("#description").val();
	var amount = $("#amount").val();
    temp = $("#type").val();
    var type; 
    switch(temp){
        case "Lodging":
            type = 1 
                break;
        case "Travel":
            type = 2;
                break;
        case "Food": 
            type = 3;
                break;
        case "Other":
            type = 4
                break;
        };
	
	//create JSON record to be sent to Servlet
    var  newRecord = [amount, currentUser.employeeID , type, description];
	var newRecordJSON = JSON.stringify(newRecord);
    console.log(newRecordJSON);
    
    //Initiate the XHR call to the servlet
 
	var xhr = new XMLHttpRequest();

	// open the xhr request, POST method, declare the route||path and set the
	// asynchronous boolean to true
	xhr.open('PUT', 'reimburse', true);

	// // send the credentials in the XHR request and wait for a response.
	xhr.send(newRecordJSON);

	xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            alert("Thank you for your request, please await a response");
            //call to load the reimbursement view page
            loadReimb();
        }

    }

};
