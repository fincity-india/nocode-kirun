import { ExecutionException } from '../../../../exception/ExecutionException';
import { StringFormatter } from '../../../../util/string/StringFormatter';
import { Operation } from '../../Operation';

export abstract class UnaryOperator {
    public abstract apply(t: any): any;

    public nullCheck(e1: any, op: Operation): void {
        if (e1 == null || !e1)
            throw new ExecutionException(
                StringFormatter.format('$ cannot be applied to a null value', op.getOperatorName()),
            );
    }
}
