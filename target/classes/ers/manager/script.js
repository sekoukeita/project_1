
let domain = "http://localhost:9000"

let userId = 0; // declare and initialize the userId variable that will be got from the session 
let idRadioInputClicked = ""; // on the form (status-2-8) with 8 the id of the reimbursement and 2 id of status

window.addEventListener("load", async () => {
    let sessionResponse = await fetch(`${domain}/session`);
    
    let sessionResult = await sessionResponse.json();

    if(!sessionResult.successful){
        window.location.href = "../"; // if no session, redirect to login page
    }else{
        if(!(sessionResult.data.roleId === 2)){
            window.location.href = "../"; // if the user logged in is not a manager, redict to the login page
        }
    }

    // get the user full name from the session result
    let fullNameElt = document.getElementById("fullname");
    fullNameElt.innerText = sessionResult.data.firstName + " " + sessionResult.data.lastName;

    // get the userId of the logged user from the session check result for later use (in createReimbursement function)
    userId = sessionResult.data.userId;

    // get the reimbursements and populate the html
        // The employee id is obtained from the session and injected in the method (see ReimbursementController)
        // The returned rebursement are for the logged in employee only
    let reimbursementsResponse = await fetch(`${domain}/managerview`);
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

        let tdEltFullName = document.createElement("td");
        tdEltFullName.innerText = reimbursement.authorFirstName + " " + reimbursement.authorLastName;
        trElt.appendChild(tdEltFullName);

        let tdEltStatus = document.createElement("td");
        tdEltStatus.innerText = reimbursement.status;
        trElt.appendChild(tdEltStatus);

        //decision radio button 1 (within its div1)
        let div1Elt = document.createElement("div");
        div1Elt.className = "form-check form-check-inline";

        let inputApprovedElt = document.createElement("input");
        inputApprovedElt.className = "form-check-input";
        inputApprovedElt.type = "radio";
        inputApprovedElt.name = `status-decision-${reimbursement.reimbursementId}`;
        inputApprovedElt.id = `status-2-${reimbursement.reimbursementId}`;
        inputApprovedElt.value = 2;

        let labApprovedElt = document.createElement("label");
        labApprovedElt.className = "form-check-label";
        labApprovedElt.for = `status-2-${reimbursement.reimbursementId}`;
        labApprovedElt.innerText = "Approved";

        div1Elt.appendChild(inputApprovedElt);
        div1Elt.appendChild(labApprovedElt);

        //decision radio button 2 (within its div2)
        let div2Elt = document.createElement("div");
        div2Elt.className = "form-check form-check-inline";
        
        let inputDeniedElt = document.createElement("input");
        inputDeniedElt.className = "form-check-input";
        inputDeniedElt.type = "radio";
        inputDeniedElt.name = `status-decision-${reimbursement.reimbursementId}`;
        inputDeniedElt.id = `status-3-${reimbursement.reimbursementId}`;
        inputDeniedElt.value = 3;
    
        let labDeniedElt = document.createElement("label");
        labDeniedElt.className = "form-check-label";
        labDeniedElt.for = `status-3-${reimbursement.reimbursementId}`;
        labDeniedElt.innerText = "Denied";

        div2Elt.appendChild(inputDeniedElt);
        div2Elt.appendChild(labDeniedElt);

        let tdEltDecision = document.createElement("td");
        tdEltDecision.appendChild(div1Elt);
        tdEltDecision.appendChild(div2Elt);
        trElt.appendChild(tdEltDecision);

        tableBodyElt.appendChild(trElt)
        
        // display a alert confirmation dialogue box.
        inputApprovedElt.onclick = function(event){
            idRadioInputClicked = event.target.id; // get the id of the input radio button clicked for update allowing to get the reimbursementId
            if (confirm(`Do you want to approve the reimbursement ${idRadioInputClicked.split('-')[2]} ?`)) {
               updateReimbursement();
               alert(`The reimbursement ${idRadioInputClicked.split('-')[2]} has been successfully approved!`);
            }
        }

        // display a alert confirmation dialogue box.
        inputDeniedElt.onclick = function(event){
            idRadioInputClicked = event.target.id;
             if (confirm(`Do you want to deny the reimbursement ${idRadioInputClicked.split('-')[2]} ?`)) {
                updateReimbursement();
                alert(`The reimbursement ${idRadioInputClicked.split('-')[2]} has been successfully denied!`);
             }
        }

        // hid the decision option for the reimbursement already aprroved or denied
        if(reimbursement.statusId != 1){div1Elt.style.visibility = "hidden"};
        if(reimbursement.statusId != 1){div2Elt.style.visibility = "hidden"};

    });

    console.log(tableBodyElt);
    let filterEltId = document.getElementsByName("radio");
}

async function logout(){
    fetch(`${domain}/logout`, {
        method: "delete"
    });

    window.location.href = "../";
}

async function filterByPending(){
    let reimbursementsResponse = await fetch(`${domain}/managerview`);
        let reimbursementsResult = await reimbursementsResponse.json();
    
        reimbursementfiltered = reimbursementsResult.filter( a => a.statusId == 1);
        populate(reimbursementfiltered);
}

async function filterByApproved(){
    let reimbursementsResponse = await fetch(`${domain}/managerview`);
        let reimbursementsResult = await reimbursementsResponse.json();
    
        reimbursementfiltered = reimbursementsResult.filter( a => a.statusId == 2);
        populate(reimbursementfiltered);
}

async function filterByDenied(){
    let reimbursementsResponse = await fetch(`${domain}/managerview`);
        let reimbursementsResult = await reimbursementsResponse.json();
    
        reimbursementfiltered = reimbursementsResult.filter( a => a.statusId == 3);
        populate(reimbursementfiltered);
}

async function updateReimbursement(){
    let reimbursementId = idRadioInputClicked.split('-')[2]; // (status-2-8) return 8
    let statusId = idRadioInputClicked.split('-')[1]; // (status-2-8) return 2
    let dateResolved = Date.now();

    let response = await fetch(`${domain}/manager/decision`, {
        method: "PATCH",
        body: JSON.stringify({
            reimbursementId: reimbursementId,
            dateResolved: dateResolved,
            resolverId: userId,
            statusId: statusId
        })
    });
}
