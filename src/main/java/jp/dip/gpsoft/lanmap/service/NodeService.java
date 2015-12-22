package jp.dip.gpsoft.lanmap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.dip.gpsoft.lanmap.model.Node;
import jp.dip.gpsoft.lanmap.repository.NodeRepository;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class NodeService {
	@Autowired
	private NodeRepository nodeRepository;

	public List<Node> findAll() {
		return nodeRepository.findAll();
	}
}
