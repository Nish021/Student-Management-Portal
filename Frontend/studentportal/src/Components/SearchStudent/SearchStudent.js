import { useState } from "react";

const SearchStudent = () => {

    const [text, setText] = useState("");

    const handleTextChange = (event) => {
        setText(event.target.value);
    }

    const handleSearch = () => {
       fetch(`http://localhost:9080/getStudentsByName/${text}`, {
        method: 'GET'
      })
      .then(response => response.json())
              .then(data => console.log('Student added:', data))
              .catch(error => console.error('Error:', error));
    }

    return (
        <div>
            <h1>Search Student</h1>
            <textarea 
            value={text}
            onChange={handleTextChange}
            rows="2" 
            cols="50" 
            placeholder="Enter student name to search"></textarea>
            <button
            onClick={handleSearch}>Search</button>
        </div>
    );
}

export default SearchStudent;