const URL = "http://localhost:8081/";

let buttonRow = document.getElementById("buttonRow");
let reimbursementButton = document.getElementById('reimbursementBtn');
// let homeButton = document.createElement("button");
let addReimbursementButton = document.getElementById('addReimbursementButton');
let loginButton = document.getElementById('loginButton');
let currentUser = null;
let confirmBtn = document.getElementById("approveDeny");
let logoutBtn = document.getElementById("logout");

confirmBtn.onclick = updateReimbursement;
reimbursementButton.onclick = getReimbursements;
// homeButton.onclick = getHomes;
addReimbursementButton.onclick = addReimbursement;
logoutBtn.onclick = logout;
loginButton.onclick = () => {
    loginToApp().then(result => {
        currentUser = result;
        // document.getElementById('loggedInStuff').style.display="inline";
        if (currentUser.roleID == 2) {
            reimbursementButton.innerText = "Get All Reimbursements"
            reimbursementButton.style.display="inline";
            reimbursementButton.style.float="left"
            document.getElementById('checkbox').style.display="inline";
            document.getElementById('approveForm').style.display='inline';
            logoutBtn.style.display="inline";
        } else {
            reimbursementButton.innerText = "Get My Reimbursements"
            reimbursementButton.style.display="inline";
            logoutBtn.style.display="inline";
        }
    }); 
}

reimbursementButton.innerText = "Get Reimbursement Requests";
homeButton.innerText = "See Homes";

function logout() {
    location.reload();
}

async function loginToApp() {
    console.log("work?")
    let user = {
        username:document.getElementById("username").value,
        password:document.getElementById("password").value
    }
    console.log("working?")
    let response = await fetch(URL+"login", {
        method:"POST",
        body:JSON.stringify(user),
        credentials:"include" //This will save the cookie when we receive it. 
    });
    console.log("works")
    if(response.status===200) {
        document.getElementsByClassName("formClass")[0].innerHTML = '';
        buttonRow.appendChild(reimbursementButton);
        document.getElementById('loggedInStuff').style.display="inline";
        // buttonRow.appendChild(homeButton);
        let result = await getCurrentUser(user.username);
        return result;
    } else {
        console.log("nowork")
        let para = document.createElement("p");
        para.setAttribute("style", "color:red")
        para.innerText = "LOGIN FAILED"
        document.getElementsByClassName("formClass")[0].appendChild(para);
    }
}

async function getCurrentUser(username) {
    let response = await fetch(URL+"users/"+username, {credentials:"include"});

    if(response.status === 200) {
        let data = await response.json();
        return data;
    } else {
        console.log("Oops! Something went wrong.");
    }
    return "yeep";
}

async function getReimbursements(){
    let response = await fetch(URL+"reimbursements", {credentials:"include"});

    if(response.status === 200) {
        let data = await response.json();
        if (currentUser.roleID == 2) {
            populateAllReimbursementsTable(data);
        } else {
            populateReimbursementsTableByUser(data);
        }
    } else {
        console.log("Oops! Something went wrong and the reimbursements disappeared.");
    }
}

async function getReimbursementByID(id) {
    let response = await fetch(URL+"reimbursements/" + id, {credentials:"include"});

    if(response.status === 200) {
        let data = await response.json();
        return data;
    } else {
        console.log("Oops! Something went wrong and the reimbursements disappeared.");
    }
}

function populateReimbursementsTableByUser(data){
    let tbody = document.getElementById("reimbursementBody");

    tbody.innerHTML="";

    for(let ticket of data){
        if (ticket.author.username == currentUser.username) {
            let row = document.createElement("tr");
            for(let cell in ticket){
                let td = document.createElement("td");
                if (cell!="author" && cell != "resolver" && cell != "statusID" && cell != "typeID") {
                    td.innerText=ticket[cell];
                } else if (cell == "author") {
                    td.innerText = `${ticket[cell].userID}: ${ticket[cell].firstName} ${ticket[cell].lastName}`
                } else if (cell == "resolver" && ticket[cell]) {
                    td.innerText = `${ticket[cell].userID}: ${ticket[cell].firstName} ${ticket[cell].lastName}`
                } else if (cell == "statusID") {
                    if (ticket[cell] == 1) {
                        td.innerText = "Pending"
                    } else if (ticket[cell] == 2) {
                        td.innerText = "Approved"
                    } else {
                        td.innerText = "Denied"
                    }
                } else {
                    if (ticket[cell] == 1) {
                        td.innerText = "Lodging"
                    } else if (ticket[cell] == 2) {
                        td.innerText = "Travel"
                    } else if (ticket[cell] == 3) {
                        td.innerText = "Food"
                    } else {
                        td.innerText = "Other"
                    }
                }
                row.appendChild(td);
            }
            tbody.appendChild(row);
        }
    }
}

function populateAllReimbursementsTable(data){
    let tbody = document.getElementById("reimbursementBody");

    tbody.innerHTML="";
    if (!document.getElementById('filter').checked) {
        for(let ticket of data){
            let row = document.createElement("tr");
            for(let cell in ticket){
                let td = document.createElement("td");
                console.log(ticket)
                console.log(cell)
                console.log(ticket[cell])
                if (cell!="author" && cell != "resolver" && cell != "statusID" && cell != "typeID") {
                    td.innerText=ticket[cell];
                } else if (cell == "author") {
                    td.innerText = `${ticket[cell].userID}: ${ticket[cell].firstName} ${ticket[cell].lastName}`
                } else if (cell == "resolver" && ticket[cell]) {
                    td.innerText = `${ticket[cell].userID}: ${ticket[cell].firstName} ${ticket[cell].lastName}`
                } else if (cell == "statusID") {
                    if (ticket[cell] == 1) {
                        td.innerText = "Pending"
                    } else if (ticket[cell] == 2) {
                        td.innerText = "Approved"
                    } else {
                        td.innerText = "Denied"
                    }
                } else {
                    if (ticket[cell] == 1) {
                        td.innerText = "Lodging"
                    } else if (ticket[cell] == 2) {
                        td.innerText = "Travel"
                    } else if (ticket[cell] == 3) {
                        td.innerText = "Food"
                    } else {
                        td.innerText = "Other"
                    }
                }
                row.appendChild(td);
            }
            tbody.appendChild(row);
        }
    } else {
        for(let ticket of data){
            if (ticket.statusID == 1) {
                let row = document.createElement("tr");
                for(let cell in ticket){
                    let td = document.createElement("td");
                    if (cell!="author" && cell != "resolver" && cell != "statusID" && cell != "typeID") {
                        td.innerText=ticket[cell];
                    } else if (cell == "author") {
                        td.innerText = `${ticket[cell].userID}: ${ticket[cell].firstName} ${ticket[cell].lastName}`
                    } else if (cell == "resolver" && ticket[cell]) {
                        td.innerText = `${ticket[cell].userID}: ${ticket[cell].firstName} ${ticket[cell].lastName}`
                    } else if (cell == "statusID") {
                        if (ticket[cell] == 1) {
                            td.innerText = "Pending"
                        } else if (ticket[cell] == 2) {
                            td.innerText = "Approved"
                        } else {
                            td.innerText = "Denied"
                        }
                    } else {
                        if (ticket[cell] == 1) {
                            td.innerText = "Lodging"
                        } else if (ticket[cell] == 2) {
                            td.innerText = "Travel"
                        } else if (ticket[cell] == 3) {
                            td.innerText = "Food"
                        } else {
                            td.innerText = "Other"
                        }
                    }
                    row.appendChild(td);
                }
                tbody.appendChild(row);
            }
        }
    }
}

async function getHomes(){
    let response = await fetch(URL+"homes", {credentials:"include"});
    if(response.status===200){
        let data = await response.json();
        populateHomeTable(data);
    }else{
        console.log("Homes not available.");
    }
}

function populateHomeTable(data){
    let tbody = document.getElementById("homeBody");

    tbody.innerHTML="";

    for(let home of data){
    let row = document.createElement("tr");
    for(let cell in home){
        let td = document.createElement("td");
        td.innerText = home[cell];
        row.appendChild(td);
    }
    tbody.appendChild(row);
    }
}

function getNewReimbursement(){
    let newAmount = document.getElementById("reimbursementAmount").value;
    let newDescription = document.getElementById("description").value; 
    let newTypeID = document.getElementById("typeID").value;

    let reimbursement =  {
    amount: newAmount,
    submitted: new Date().toISOString(),
    description: newDescription,
    author: currentUser,
    statusID: 1,
    typeID: newTypeID
    }

    return reimbursement;
}

function hashCode(str) {
    return Array.from(str)
        .reduce((s, c) => Math.imul(31, s) + c.charCodeAt(0) | 0, 0)
}

async function addReimbursement() {
    let reimbursement = getNewReimbursement();

    let response = await fetch(URL+"reimbursements", {
        method:'POST',
        body:JSON.stringify(reimbursement),
        credentials:"include"
    });

    if (response.status===201) {
        console.log("Reimbursement successfully submitted.");
    } else {
        console.log("Something went wrong creating your request.")
    }

}

async function updateReimbursement() {
    let reimbursement = await getUpdatedReimbursement();
    console.log(reimbursement)
    let response = await fetch(URL+"reimbursements", {
        method:'PUT',
        body:JSON.stringify(reimbursement),
        credentials:"include"
    });
    
    if (response.status===200) {
        console.log("Request successfully completed.");
    } else {
        console.log("Something went wrong with your request.")
    }
}

async function getUpdatedReimbursement() {
    let id = document.getElementById("reimbursementID").value;
    let approve = document.getElementById("completeRequest").value;
    let author = await getReimbursementByID(id);
    let reimbursement = {
        id: id,
        resolved: new Date().toISOString(),
        resolver: currentUser,
        statusID: approve,
        author: author.author
    }
    return reimbursement;
}