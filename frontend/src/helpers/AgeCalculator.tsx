export  function  AgeCalculator(birthday: string){
    const userDate = new Date(birthday);
    const today = new Date();
    let age = today.getFullYear() - userDate.getFullYear();
    const month = today.getMonth() - userDate.getMonth();
    if(month < 0 || (month === 0 && today.getDate() < userDate.getDate())){
        age = age -1;
    }
    return age;
}