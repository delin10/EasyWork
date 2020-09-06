!=MAPPER_XML
<#assign idCol="${table.id.name}">
<#assign idField="${colFieldMap[idCol].name}">
<#assign modelFullName="${root.basePkg}.model.${entity.name}">
<#function isCollection type>
    <#return type?starts_with("Set") || type?starts_with("List") || type?starts_with("Collection")>
</#function>
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${root.basePkg}.dao.${entity.name}Mapper">
    <resultMap id="resultMap" type="${modelFullName}">
        <#list entity.fields as field>
        <<#if field.primary>id<#else>result</#if> column="${fieldColMap[field.name].name}" property="${field.name}" jdbcType="${fieldColMap[field.name].type?upper_case}"/>
        </#list>
    </resultMap>

    <sql id="TABLE_NAME">
        `${table.name}`
    </sql>

    <sql id="INSERT_COLS">
        <#list entity.fields as field>
        `${fieldColMap[field.name].name}`<#sep>,
        </#sep>
        </#list>

    </sql>

    <sql id="INSERT_VALUES">
        #{insertFields?size}
        <#if (insertFields?size) = 0 >
            <#list entity.fields as field>
        <#noparse>#{</#noparse>${field.name}<#sep><#noparse>}</#noparse>,</#sep>
            </#list>
        <#else>
            <#list insertFields as field>
        <#noparse>#{</#noparse>${field.name}<#sep><#noparse>}</#noparse>,</#sep>
            </#list>
        </#if>
    </sql>

    <insert id="insert" parameterType="${modelFullName}" useGeneratedKeys="true"
            keyProperty="${idField}">
        insert into
        <include refid="TABLE_NAME"/>(<include refid="INSERT_COLS"/>)
        values(<include refid="INSERT_VALUES"/>)
    </insert>

    <sql id="WHERE_ID">
        <where>
            `id` = '<#noparse>#{</#noparse>${idField}<#noparse>}</#noparse>'
        </where>
    </sql>

    <update id="update" parameterType="${modelFullName}">
        update <include refid="TABLE_NAME"/>
        <set>
            <#list entity.fields as field>
            `${fieldColMap[field.name].name}` = <#noparse>#{</#noparse>${field.name}<#noparse>}</#noparse><#sep>,
            </#sep>
            </#list>

        </set>
        <include refid="WHERE_ID"/>
    </update>

    <sql id="LIST_WHERE">
        <#list listFields as field>
            <#if searchFields?seq_contains(field)>
        and ${fieldColMap[field]} like concat('%', <#noparse>#{</#noparse>query<#noparse>}</#noparse>, '%')
            <#elseif isCollection('${fieldDescs[field+"-list"].type}')>
        <if test = "${fieldDescs[field+"-list"].name} != null and ${fieldDescs[field+"-list"].name}.size > 0">
            and ${fieldDescs[field+"-list"].originName} in
            <foreach collection="${fieldDescs[field+"-list"].name}" item="item" open="(" close=")" seperator=",">
            <#noparse>{</#noparse>item<#noparse>}</#noparse>
            </foreach>
        </if>
            <#else>
        <if test="${field} != null}">
            and ${fieldColMap[field].name}='<#noparse>#{</#noparse>${field}<#noparse>}</#noparse>'
        </if>
            </#if>
        </#list>
    </sql>

    <select id="list" resultMap="resultMap">
        select *
        from  <include refid="TABLE_NAME"/>
        <include ref="LIST_WHERE"/>
        order by ${idCol}
        <if test="size < 0">
            limit <#noparse>#{</#noparse>start<#noparse>}</#noparse>,<#noparse>#{</#noparse>size<#noparse>}</#noparse>
        </if>
    </select>

    <select id="count" resultType="long">
        select count(1)
        from  <include refid="TABLE_NAME"/>
        <include ref="LIST_WHERE"/>
    </select>
</mapper>