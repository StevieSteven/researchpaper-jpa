package eu.lestard.bloggie.graphql;

import java.util.Map;

public class GraphQLInputQuery {

    private String query;
    private Map<String, Object> variables;

    public GraphQLInputQuery() {}

    public GraphQLInputQuery(String query, Map<String, Object> variables) {
        this.query = query;
        this.variables = variables;
    }

    public String getQuery() {
        return query;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }
}
