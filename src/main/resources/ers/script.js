
let domain = "http://localhost:9000"


async function login(e){

    e.preventDefault();

    let usernameElt =  document.getElementById("username-input");
    let passwordElt = document.getElementById("password-input");
    
    //send the username and password got from the form body to the server
    // userName and password are the field name in the user model.
    let response = await fetch(`${domain}/login`, {
        method: "POST",
        body: JSON.stringify({
            userName: usernameElt.value,
            password: passwordElt.value,
        })
    });

   let result = await response.json();
   
   // the JsonResponse object is returned as result (see LoginController)
   if(!result.successful){
       alert(result.message);
       window.location.href = "./"; // if no login saved, go to the login page
   }
   else{
       if(result.data.roleId == 1){
            window.location.href = "./employee/request"; // if the login saved if for an employee, go to the employee request page.
       }
       else if(result.data.roleId == 2){
            window.location.href = "./manager"; // if the login saved if for a manager, go to the manager page.
       }
       else{
        window.location.href = "./";
       }
   }
   
    
    




}