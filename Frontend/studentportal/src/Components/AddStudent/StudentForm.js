const StudentForm = () => {

    const handleFormSubmit = (event) => {
        event.preventDefault();
        const formData = new FormData(event.target);
        const student = {
            name: formData.get("name"),
            age: formData.get("age"),
            className: formData.get("grade"),
            phoneNumber: formData.get("phonenumber")
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
              .then(data => console.log('Student added:', data))
              .catch(error => console.error('Error:', error));
    }
    return (
        <div className="student-form-container">
            <form onSubmit={handleFormSubmit}>
                <label>
                    Name:
                    <input type="text" name="name" />
                </label>
                <br />
                <label>
                    Age:
                    <input type="text" name="age" />
                </label>
                <br />
                <label>
                    Grade:
                    <input type="text" name="grade" />
                </label>
                <br />
                <label>
                    Phone Number:
                    <input type="number" name="phonenumber" />
                </label>
                <br />
                <button type="submit">Add Student</button>
            </form>
        </div>
    );
}

export default StudentForm;