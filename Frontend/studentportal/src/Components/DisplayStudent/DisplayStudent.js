// import { useEffect, useState } from "react";
// import Student from "../Student/Student";

// const DisplayStudent = () => {

//     const [students, setStudents] = useState([]);   
//     useEffect(() => {
//         fetch(`http://localhost:9080/getAllStudentRecords`, {
//             method: 'GET'
//         })
//         .then(response => response.json())
//         .then(data => {
//             console.log('Students:', data);
//             setStudents(data);
//         })
//         .catch(error => console.error('Error:', error));
//     }, []);
//     return (
//         <>
//         <div>Display All Students</div>
//         {students.map((student) => (
//             <div key={student.id}>
//                 <p>{student.name}</p>
//                 <p>{student.age}</p>
//                 <p>{student.grade}</p>
//             </div>
//         ))}
//         </>
//     );
// }

// export default DisplayStudent;