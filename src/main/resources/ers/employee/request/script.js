let domain = "http://localhost:9000"

let userId = 0; // declare and initialize the userId variable that will be got from the session 

window.addEventListener("load", async () => {
    let sessionResponse = await fetch(`${domain}/session`);
    
    let sessionResult = await sessionResponse.json();

    if(!sessionResult.successful){
        window.location.href = "../../"; // if no session, redirect to login page
    }else{
        if(!(sessionResult.data.roleId === 1)){
            window.location.href = "../../"; // if the user logged in is not an employee, redict to the login page
        }
    }

    // get the user full name from the session result
    let fullNameElt = document.getElementById("fullname");
    fullNameElt.innerText = sessionResult.data.firstName + " " + sessionResult.data.lastName;

    // get the userId of the logged user from the session check result for later use (in createReimbursement function)
    userId = sessionResult.data.userId;

});


async function logout(){
    fetch(`${domain}/logout`, {
        method: "delete"
    });

    window.location.href = "../../";
}

async function createReimbursement(e){
    e.preventDefault();

    let amountElt = document.getElementById("amount-input");
    let dateSubmittedElt = document.getElementById("dateSubmitted-input");
    let descriptionElt = document.getElementById("description-input");
    let typeIdElt = Array.from(document.getElementsByName("flexRadioDefault")).find(r => r.checked)

    await fetch(`${domain}/request`, {
        method: "POST",
        body: JSON.stringify({
            amount: amountElt.value, 
            dateSubmitted: dateSubmittedElt.value,
            description: descriptionElt.value,
            authorId: userId, // get from the session
            statusId: 1, // set to 1 because a created request is always pending (statusId = 1)
            typeId: typeIdElt.value
        })
    });
    
    //alert("The request has been successfully submitted!.")
    amountElt.value = "";
    dateSubmittedElt.value = "";
    descriptionElt.value = "";
}

