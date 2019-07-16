package com.netcracker.CoffeeShopApplication.ordermanagement.services;

import com.netcracker.CoffeeShopApplication.ordermanagement.models.DbSequence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
public class SequenceServiceImpl implements SequenceService {
    @Autowired
    MongoOperations mongoOp;

    @Override
    public long getNextSequenceNo(String name) {
        Query query = new Query(Criteria.where("_id").is(name));
        Update update = new Update();
        update.inc("seq", 1);
        DbSequence modify = mongoOp.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), DbSequence.class);
        return modify.getSeq();
    }

    public boolean sequenceExist(String seqName) {
        Query query = new Query(Criteria.where("_id").is(seqName));
        DbSequence one = mongoOp.findOne(query, DbSequence.class);
        return one != null;
    }

    @Override
    public void saveNewSequence(String name, long initValue) {
        DbSequence sequence=new DbSequence();
        sequence.setName(name);
        sequence.setSeq(initValue);
        mongoOp.save(sequence);
    }
}
