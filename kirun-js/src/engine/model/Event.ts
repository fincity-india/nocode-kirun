import { AdditionalPropertiesType } from '../json/schema/object/AdditionalPropertiesType';
import { Schema } from '../json/schema/Schema';
import { SchemaType } from '../json/schema/type/SchemaType';
import { TypeUtil } from '../json/schema/type/TypeUtil';
import { Namespaces } from '../namespaces/Namespaces';

export class Event {
    public static readonly OUTPUT: string = 'output';
    public static readonly ERROR: string = 'error';
    public static readonly ITERATION: string = 'iteration';
    public static readonly TRUE: string = 'true';
    public static readonly FALSE: string = 'false';
    public static readonly SCHEMA_NAME: string = 'Event';
    public static readonly SCHEMA: Schema = new Schema()
        .setNamespace(Namespaces.SYSTEM)
        .setName(Event.SCHEMA_NAME)
        .setType(TypeUtil.of(SchemaType.OBJECT))
        .setProperties(
            new Map([
                ['name', Schema.ofString('name')],
                [
                    'parameters',
                    Schema.ofObject('parameter').setAdditionalProperties(
                        new AdditionalPropertiesType().setSchemaValue(Schema.SCHEMA),
                    ),
                ],
            ]),
        );
    private name: string;
    private parameters: Map<string, Schema>;

    public getName(): string {
        return this.name;
    }
    public setName(name: string): Event {
        this.name = name;
        return this;
    }
    public getParameters(): Map<string, Schema> {
        return this.parameters;
    }
    public setParameters(parameters: Map<string, Schema>): Event {
        this.parameters = parameters;
        return this;
    }

    public static outputEventMapEntry(parameters: Map<string, Schema>): [string, Event] {
        return Event.eventMapEntry(Event.OUTPUT, parameters);
    }

    public static eventMapEntry(
        eventName: string,
        parameters: Map<string, Schema>,
    ): [string, Event] {
        return [eventName, new Event().setName(eventName).setParameters(parameters)];
    }
}
