package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.UserDocuments;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDocumentsRepository extends CrudRepository<UserDocuments, Integer> {
}
