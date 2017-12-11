package eu.lestard.bloggie.graphql;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExecutionResult;
import org.crygier.graphql.GraphQLExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class GraphQLController {

    @Autowired
    private GraphQLExecutor graphQLExecutor;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(path="/api/graphql", method= RequestMethod.POST)
    public ExecutionResult graphQl(@RequestBody GraphQLInputQuery query) throws IOException {
        final String queryString = query.getQuery();

        final Map<String, Object> variables = query.getVariables();

        if(variables == null) {
            return graphQLExecutor.execute(queryString);
        } else {
            return graphQLExecutor.execute(queryString, variables);
        }
    }
}
