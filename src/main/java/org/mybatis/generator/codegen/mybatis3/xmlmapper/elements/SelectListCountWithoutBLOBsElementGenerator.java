package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * @author <a href="mailto:sanjing#shulie.io">mitsui</a>
 */
public class SelectListCountWithoutBLOBsElementGenerator extends AbstractXmlElementGenerator {

    public SelectListCountWithoutBLOBsElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {

        String baseRecordType = introspectedTable.getBaseRecordType();
        XmlElement answer = new XmlElement("select");
        answer.addAttribute(new Attribute("id", introspectedTable.getSelectListCountStatementId()));
        answer.addAttribute(new Attribute("resultMap", introspectedTable.getBaseResultMapId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("parameterType", baseRecordType)); //$NON-NLS-1$
        context.getCommentGenerator().addComment(answer);
//        answer.addElement(new TextElement("select")); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        if (stringHasValue(introspectedTable.getSelectByExampleQueryId())) {
            sb.append('\'');
            sb.append(introspectedTable.getSelectByExampleQueryId());
            sb.append("' as QUERYID,"); //$NON-NLS-1$
            answer.addElement(new TextElement(sb.toString()));
        }
//        answer.addElement(getBaseColumnListElement());

        sb.setLength(0);
        sb.append("select count (*) from "); //$NON-NLS-1$
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());

        List<IntrospectedColumn> introspectedColumns = introspectedTable.getBaseColumns();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (introspectedColumn.getJavaProperty().equals("isDeleted")) {
                if (introspectedColumn.getJdbcTypeName().equals("VARCHAR")) {
                    sb.append(" where is_deleted = 'N'");
                } else if (introspectedColumn.getJdbcTypeName().equals("BYTE")) {
                    sb.append(" where is_deleted = '0'");
                }
            }
        }

        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getDataWhereClauseElement());

        if (context.getPlugins().sqlMapSelectByExampleWithoutBLOBsElementGenerated(answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
