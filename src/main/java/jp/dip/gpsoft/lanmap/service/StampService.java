package jp.dip.gpsoft.lanmap.service;

import java.sql.Timestamp;

import org.springframework.stereotype.Service;

import jp.dip.gpsoft.lanmap.model.Stamp;

@Service
public class StampService {

	void stamp(Stamp stamp, Timestamp now) {
		if (stamp.getCreated() == null) {
			stamp.setCreated(now);
		}
		stamp.setModified(now);
	}
}
