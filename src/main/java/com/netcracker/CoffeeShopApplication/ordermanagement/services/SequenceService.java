package com.netcracker.CoffeeShopApplication.ordermanagement.services;

public interface SequenceService {
    public long getNextSequenceNo(String name);

    public boolean sequenceExist(String seqName);

    public void saveNewSequence(String name, long initValue);
}
