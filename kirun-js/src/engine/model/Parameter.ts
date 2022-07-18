import { Schema } from '../json/schema/Schema';
import { SchemaType } from '../json/schema/type/SchemaType';
import { Namespaces } from '../namespaces/Namespaces';
import { ParameterType } from './ParameterType';

export class Parameter {
    private static readonly SCHEMA_NAME: string = 'Parameter';
    public static readonly SCHEMA: Schema = new Schema()
        .setNamespace(Namespaces.SYSTEM)
        .setName(Parameter.SCHEMA_NAME)
        .setProperties(
            new Map([
                ['schema', Schema.SCHEMA],
                ['parameterName', Schema.ofString('parameterName')],
                [
                    'variableArgument',
                    Schema.of('variableArgument', SchemaType.BOOLEAN).setDefaultValue(false),
                ],
            ]),
        );
    private schema: Schema; // NOSONAR - this is really getting on my nerves,I have a use case for same

    private parameterName: string;
    private variableArgument: boolean = false;
    private type: ParameterType = ParameterType.EXPRESSION;

    public getSchema(): Schema {
        return this.schema;
    }
    public setSchema(schema: Schema): Parameter {
        this.schema = schema;
        return this;
    }
    public getParameterName(): string {
        return this.parameterName;
    }
    public setParameterName(parameterName: string): Parameter {
        this.parameterName = parameterName;
        return this;
    }
    public isVariableArgument(): boolean {
        return this.variableArgument;
    }
    public setVariableArgument(variableArgument: boolean): Parameter {
        this.variableArgument = variableArgument;
        return this;
    }
    public getType(): ParameterType {
        return this.type;
    }
    public setType(type: ParameterType): Parameter {
        this.type = type;
        return this;
    }
}