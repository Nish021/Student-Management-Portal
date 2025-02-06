import './AddStudent.css';
import { useState } from "react";

const AddStudent = () => {

    const [formData, setFormData] = useState({
        name: '',
        age: '',
        class: '',
        phonenumber: '',
    });
    
    const [errors, setErrors] = useState({
        name: '',
        age: '',
        class: '',
        phonenumber: '',
    });

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const isValidForm = () => {
        const newErrors = {};
        let isValid = true;
    
        if (!formData.name) {
          newErrors.name = "Student name is required";
          isValid = false;
        }
    
        if (!formData.age) {
          newErrors.age = "Student age is required";
          isValid = false;
        }
    
        if (!formData.class) {
          newErrors.class = "Class is required";
          isValid = false;
        }
    
        if (!formData.phonenumber || formData.phonenumber.length !== 10) {
          newErrors.phonenumber = "Please enter a valid 10-digit phone number";
          isValid = false;
        }
    
        setErrors(newErrors);
        return isValid;
    }



    const handleFormSubmit = (event) => {
        event.preventDefault();
        if(isValidForm()){
            const student = {
                name: formData.name,
                age: formData.age,
                className: formData.class,
                phoneNumber: formData.phonenumber
            };
            console.log(student);
            fetch('http://localhost:9080/createStudentRecord', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(student)
                })
                .then(response => response.json())
                .then(data => {
                    console.log('Student added:', data);
                    // setFormData({
                    //     name: '',
                    //     age: '',
                    //     class: '',
                    //     phonenumber: '',
                    // });
                    })
                .catch(error => console.error('Error:', error));
        }
    }
    return (
        <div className="add-student-container">
           <div className="add-student">
            <div className="student-form-container">
                    <form onSubmit={handleFormSubmit}>
                        <label>
                            <div className="row">
                                <div className="col-1">Name</div>
                                <div className="col-2">
                                    <input 
                                        type="text" 
                                        name="name"
                                        value={formData.name}
                                        onChange={handleInputChange} 
                                    />
                                </div>
                            </div>
                        </label>
                        {errors.name && <p className="error">{errors.name}</p>}
                        <br />
                        <label>
                            <div className="row">
                                <div className="col-1">Age</div>
                                <div className="col-2">
                                    <input 
                                        type="text" 
                                        name="age"
                                        value={formData.age}
                                        onChange={handleInputChange} 
                                    />
                                </div>
                            </div>
                        </label>
                        {errors.age && <p className="error">{errors.age}</p>}
                        <br />
                        <label>
                            <div className="row">
                                <div className="col-1">Class</div>
                                <div className="col-2">
                                    <input 
                                        type="text" 
                                        name="class" 
                                        value={formData.class}
                                        onChange={handleInputChange} 
                                    />
                                </div>
                            </div>
                        </label>
                        {errors.class && <p className="error">{errors.class}</p>}
                        <br />
                        <label>
                            <div className="row">
                                <div className="col-1">Phone Number</div>
                                <div className="col-2">
                                    <input
                                         type="number" 
                                         name="phonenumber" 
                                         value={formData.phonenumber}
                                         onChange={handleInputChange} 
                                    />
                                </div>
                            </div>
                        </label>
                        {errors.phonenumber && <p className="error">{errors.phonenumber}</p>}
                        <br />
                        <button type="submit">Add Student</button>
                    </form>
             </div>
            </div>
        </div>
    );
}

export default AddStudent;