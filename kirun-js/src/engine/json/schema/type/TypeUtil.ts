import { MultipleType } from './MultipleType';
import { SchemaType } from './SchemaType';
import { SingleType } from './SingleType';
import { Type } from './Type';

export class TypeUtil {
    public static of(...types: SchemaType[]): Type {
        if (types.length == 1) return new SingleType(types[0]);

        return new MultipleType().setType(new Set(types));
    }
}