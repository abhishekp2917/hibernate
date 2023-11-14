/*
    Defining custom MYSQL dialect in which one can customize as per requirements and use this class in
    hibernate or persistence config file
*/

package org.example.dialect;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StringType;

public class CustomMySQLDialect extends MySQL5Dialect {

    public CustomMySQLDialect() {
        super();
        registerFunction("group_concat", new StandardSQLFunction("group_concat", StringType.INSTANCE));
    }

    @Override
    public String getTableTypeString() {
        // Specify the storage engine here
        return " ENGINE=MyISAM";
    }
}
