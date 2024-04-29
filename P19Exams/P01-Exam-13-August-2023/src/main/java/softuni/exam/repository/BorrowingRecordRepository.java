package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.BorrowingRecord;

import java.util.List;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord,Long> {

    @Query("SELECT b FROM BorrowingRecord b " +
            "WHERE b.borrowDate < '2021-09-10' AND " +
            "b.book.genre = 'SCIENCE_FICTION' " +
            "ORDER BY  b.borrowDate DESC")
    List<BorrowingRecord> findBorrowingRecordsInfo();
}
