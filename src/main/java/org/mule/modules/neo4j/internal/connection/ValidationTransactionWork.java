package org.mule.modules.neo4j.internal.connection;

import org.neo4j.driver.v1.Transaction;
import org.neo4j.driver.v1.TransactionWork;

public class ValidationTransactionWork implements TransactionWork<Boolean>{

    @Override
    public Boolean execute(Transaction transaction) {
        transaction.run("MATCH (a) RETURN a LIMIT 1");
        return true;
    }
}
