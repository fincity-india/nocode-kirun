import { Repository } from '../../../Repository';
import { Schema } from '../Schema';
import { SchemaType } from '../type/SchemaType';
import { ArrayValidator } from './ArrayValidator';
import { BooleanValidator } from './BooleanValidator';
import { SchemaValidationException } from './exception/SchemaValidationException';
import { NullValidator } from './NullValidator';
import { NumberValidator } from './NumberValidator';
import { ObjectValidator } from './ObjectValidator';
import { SchemaValidator } from './SchemaValidator';
import { StringValidator } from './StringValidator';

export class TypeValidator {
    public static validate(
        parents: Schema[],
        type: SchemaType,
        schema: Schema,
        repository: Repository<Schema>,
        element: any,
    ): any {
        if (type == SchemaType.STRING) {
            StringValidator.validate(parents, schema, element);
        } else if (
            type == SchemaType.LONG ||
            type == SchemaType.INTEGER ||
            type == SchemaType.DOUBLE ||
            type == SchemaType.FLOAT
        ) {
            NumberValidator.validate(type, parents, schema, element);
        } else if (type == SchemaType.BOOLEAN) {
            BooleanValidator.validate(parents, schema, element);
        } else if (type == SchemaType.OBJECT) {
            ObjectValidator.validate(parents, schema, repository, element);
        } else if (type == SchemaType.ARRAY) {
            ArrayValidator.validate(parents, schema, repository, element);
        } else if (type == SchemaType.NULL) {
            NullValidator.validate(parents, schema, element);
        } else {
            throw new SchemaValidationException(
                SchemaValidator.path(parents),
                type + ' is not a valid type.',
            );
        }

        return element;
    }

    private constructor() {}
}
