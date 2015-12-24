package jp.dip.gpsoft.lanmap.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jp.dip.gpsoft.lanmap.model.Node;

@Repository
public interface NodeRepository extends JpaRepository<Node, Long> {

	@Query("SELECT n FROM Node n WHERE n.deleted=0 ORDER BY n.acquired DESC")
	public List<Node> findAllAlive();
}
