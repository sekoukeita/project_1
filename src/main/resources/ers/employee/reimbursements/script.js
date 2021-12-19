
let domain = "http://localhost:9000"

let userId = 0;

window.addEventListener("load", async () => {
    let sessionResponse = await fetch(`${domain}/session`);
    let sessionResult = await sessionResponse.json();

    if(!sessionResult.successful){
        window.location.href = "../../";
    }else{
        if(!(sessionResult.data.roleId === 1)){
            window.location.href = "../../";
        }
    }

    // get full name from the session
    let fullNameElt = document.getElementById("fullname");
    fullNameElt.innerText = sessionResult.data.firstName + " " + sessionResult.data.lastName;

    // get the reimbursements and populate the html
        // The employee id is obtained from the session and injected in the method (see ReimbursementController)
        // The returned rebursement are for the logged in employee only
    let reimbursementsResponse = await fetch(`${domain}/reimbursements`);
    let reimbursementsResult = await reimbursementsResponse.json();

    reimbursementsResult.sort((a, b) => (a.reimbursementId < b.reimbursementId) ? -1 : 1); // sort in ascending order of reimbursementId

    populate(reimbursementsResult);

});

function populate(reimbursements){

    let tableBodyElt = document.getElementsByTagName("tbody")[0];
    tableBodyElt.innerHTML = "";

    reimbursements.forEach(reimbursement => {

        let trElt = document.createElement("tr");

       // set the row class according to statusId following boostrap classes naming convention for table rows
            // pending: gray, approved: green, denie: red.
        if(reimbursement.statusId == 1){
            trElt.className = "table-secondary";
        }else if(reimbursement.statusId == 2){
            trElt.className = "table-success";
        }else{
            trElt.className = "table-danger";
        }

        let tdEltId = document.createElement("td");
        tdEltId.innerHTML = reimbursement.reimbursementId;
        trElt.appendChild(tdEltId);

        let tdEltType = document.createElement("td");
        tdEltType.innerHTML = reimbursement.type;
        trElt.appendChild(tdEltType);

        let tdEltDescription = document.createElement("td");
        tdEltDescription.innerText = reimbursement.description;
        trElt.appendChild(tdEltDescription);

        let tdEltAmount = document.createElement("td");
        tdEltAmount.innerText = "$" + reimbursement.amount;
        trElt.appendChild(tdEltAmount);

        let tdEltDateSubmitted = document.createElement("td");
        tdEltDateSubmitted.innerText = reimbursement.dateSubmitted;
        trElt.appendChild(tdEltDateSubmitted);

        let tdEltDateResolved = document.createElement("td");
        tdEltDateResolved.innerText = reimbursement.dateResolved;
        trElt.appendChild(tdEltDateResolved);

        let tdEltFullName = document.createElement("td");
        if(reimbursement.statusId == 1){
            tdEltFullName.innerText = "";
        }
        else{
            tdEltFullName.innerText = reimbursement.resolverFirstName + " " + reimbursement.resolverLastName
        }
        trElt.appendChild(tdEltFullName);

        let tdEltStatus = document.createElement("td");
        tdEltStatus.innerText = reimbursement.status;
        trElt.appendChild(tdEltStatus);

        tableBodyElt.appendChild(trElt)  
    })
}

async function logout(){
    fetch(`${domain}/logout`, {
        method: "delete"
    });

    window.location.href = "../../";
}
