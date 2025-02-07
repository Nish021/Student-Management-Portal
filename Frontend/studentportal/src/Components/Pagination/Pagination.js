
import React from 'react';
import './Pagination.css';

const Pagination = ({ currentPage, totalPages, setCurrentPage }) => {
    const pageNumbers = [];
    for (let i = 0; i <= totalPages; i++) {
        pageNumbers.push(i);
    }

    return (
        <nav>
            <ul className="pagination">
                {pageNumbers.map(number => (
                    <li key={number} className={`page-item ${currentPage === number ? 'active' : ''}`}>
                        <button onClick={() => setCurrentPage(number)} className="page-link">
                            {number}
                        </button>
                    </li>
                ))}
            </ul>
        </nav>
    );
}

export default Pagination;