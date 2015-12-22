package jp.dip.gpsoft.lanmap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.dip.gpsoft.lanmap.model.Node;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {
}
