package nil.ed.easywork.sql.parser;

import com.alibaba.druid.sql.ast.statement.SQLCreateStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlCreateTableParser;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlExprParser;
import com.alibaba.druid.sql.parser.SQLCreateTableParser;
import com.alibaba.druid.sql.parser.SQLDDLParser;
import nil.ed.easywork.sql.enums.DbType;
import org.h2.command.Command;
import org.h2.command.Parser;
import org.h2.engine.ConnectionInfo;
import org.h2.engine.Database;
import org.h2.engine.Session;
import org.h2.table.Table;
import org.junit.Assert;
import org.junit.Test;

public class ShardingsphereSQLParserImplTest {

    private ShardingsphereSQLParserImpl shardingsphereSQLParser = new ShardingsphereSQLParserImpl(DbType.MYSQL);

    @Test
    public void parse() {
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
                "    unique key `name_unique_index`(`username`, `nickname`)\n" +
                ")engine=innodb default character set utf8mb4 comment '用户表';");
        System.out.println(shardingsphereSQLParser.parse(sql));
//        ConnectionInfo connectionInfo = new ConnectionInfo();
//        Database database = new Database();
        Parser parser = new Parser();
        Command command = parser.prepareCommand("create table `t_user`(\n" +
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
                "    unique key `name_unique_index`(`username`, `nickname`)\n" +
                ")engine=innodb default character set utf8mb4 comment '用户表';");
        System.out.println(command);
    }

    @Test
    public void parseComment() {

        String sql0 = "`username` varchar(20) not null comment '用户名'";
        String sql1 = "`username` varchar(20) not null";
        String sql2 = ("create table `t_user`(\n" +
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
                "    index `name_unique_index`(`username`, nickname),\n" +
                "    unique index `name_unique_index`(`username`, nickname),\n" +
                "    unique key `name_unique_index`(`username`, nickname),\n" +
                "    key `name_unique_index`(`username`, nickname)\n" +
                ")engine=innodb default character set utf8mb4 comment '用户表';");
        System.out.println(shardingsphereSQLParser.parseComment(sql0));
        System.out.println(shardingsphereSQLParser.parseComment(sql2));
        Assert.assertFalse(shardingsphereSQLParser.parseComment(sql0).isEmpty());
        Assert.assertTrue(shardingsphereSQLParser.parseComment(sql1).isEmpty());
    }
}
