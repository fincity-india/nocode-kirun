import { ExecutionException } from '../../../../exception/ExecutionException';
import { isNullValue } from '../../../../util/NullCheck';
import { StringFormatter } from '../../../../util/string/StringFormatter';
import { Operation } from '../../Operation';

export abstract class TernaryOperator {
    public abstract apply(t: any, u: any, v: any): any;

    public nullCheck(e1: any, e2: any, e3: any, op: Operation): void {
        if (isNullValue(e1) || isNullValue(e2) || isNullValue(e3))
            throw new ExecutionException(
                StringFormatter.format('$ cannot be applied to a null value', op.getOperatorName()),
            );
    }
}
