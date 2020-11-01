package nil.ed.easywork.sql;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.shardingsphere.sql.parser.SQLParserEngineFactory;
import org.apache.shardingsphere.sql.parser.core.parser.SQLParserExecutor;
import org.apache.shardingsphere.sql.parser.core.visitor.ParseTreeVisitorFactory;
import org.apache.shardingsphere.sql.parser.core.visitor.VisitorRule;
import org.apache.shardingsphere.sql.parser.sql.segment.ddl.column.ColumnDefinitionSegment;
import org.apache.shardingsphere.sql.parser.sql.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.sql.statement.ddl.CreateTableStatement;

import javax.sound.midi.Soundbank;

/**
 * @author delin10
 * @since 2020/5/18
 **/
public class Test {
    public static void main(String[] args) {
        String sql = ("create table `t_user`(\n" +
                "    `id` bigint unsigned auto_increment not null comment 'id',\n" +
                "    `username` varchar(20) not null comment '用户名',\n" +
                "    `nickname` varchar (50) default '' comment '昵称',\n" +
                "    `pwd` varchar(100) default '' not null comment '密码',\n" +
                "    `tel` varchar(20) default '' not null comment '电话号码',\n" +
                "    `actual_name` varchar(50) default '' not null comment '真实姓名',\n" +
                "    `identifier_id` varchar(30) default '' not null comment '身份证号码',\n" +
                "    `head_img` varchar(512) default '' not null comment '头像地址',\n" +
                "    `update_time` TIMESTAMP default current_timestamp on update current_timestamp comment '更新时间',\n" +
                "    `create_time` TIMESTAMP default current_timestamp comment '创建时间',\n" +
                "    primary key (`id`),\n" +
                "    unique key `name_unique_index`(`username`)\n" +
                ")engine=innodb default character set utf8mb4 comment '用户表';");
        System.out.println(sql);
        SQLStatement actual = SQLParserEngineFactory.getSQLParserEngine("MySQL").parse(sql, false);
        CreateTableStatement createTableStatement = (CreateTableStatement)actual;
        for (ColumnDefinitionSegment col : createTableStatement.getColumnDefinitions()) {
            System.out.println(col.getColumnName().getIdentifier().getValue());
            System.out.println(col.getDataType().getDataTypeName());
            System.out.println(col.isPrimaryKey());
            System.out.println(sql.substring(col.getStartIndex(), col.getStopIndex()));
        }


    }
}
