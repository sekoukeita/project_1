
let domain = "http://localhost:9000"

window.addEventListener("load", async () => {
    let response = await fetch(`${domain}/session`);
    let result = await response.json();
    console.log(result);

    if(!result.successful){
        window.location.href = "../";
    }else{
        if(!(result.data.roleId === 2)){
            window.location.href = "../";
        }
    }

    let fullNameElt = document.getElementById("fullname");
   
    fullNameElt.innerText = result.data.firstName + " " + result.data.lastName;

});

async function logout(){
    fetch(`${domain}/logout`, {
        method: "delete"
    });

    window.location.href = "../";
}
