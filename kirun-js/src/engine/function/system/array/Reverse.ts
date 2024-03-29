import { KIRuntimeException } from '../../../exception/KIRuntimeException';
import { EventResult } from '../../../model/EventResult';
import { FunctionOutput } from '../../../model/FunctionOutput';
import { FunctionExecutionParameters } from '../../../runtime/FunctionExecutionParameters';
import { AbstractArrayFunction } from './AbstractArrayFunction';

export class Reverse extends AbstractArrayFunction {
    public constructor() {
        super(
            'Reverse',
            [
                Reverse.PARAMETER_ARRAY_SOURCE,
                Reverse.PARAMETER_INT_SOURCE_FROM,
                Reverse.PARAMETER_INT_LENGTH,
            ],
            Reverse.EVENT_RESULT_ARRAY,
        );
    }

    protected async internalExecute(context: FunctionExecutionParameters): Promise<FunctionOutput> {
        let source: any[] = context
            ?.getArguments()
            ?.get(Reverse.PARAMETER_ARRAY_SOURCE.getParameterName());

        let st: number = context
            ?.getArguments()
            ?.get(Reverse.PARAMETER_INT_SOURCE_FROM.getParameterName());

        let len: number = context
            ?.getArguments()
            ?.get(Reverse.PARAMETER_INT_LENGTH.getParameterName());

        if (len == -1) len = source.length - st;

        if (len >= source.length || len < 0 || st < 0)
            throw new KIRuntimeException(
                'Please provide start point between the start and end indexes or provide the length which was less than the source size ',
            );

        source = [...source];

        let endpoint: number = st + len - 1;
        while (st <= endpoint) {
            let first: any = source[st];
            let last: any = source[endpoint];
            source[st++] = last;
            source[endpoint--] = first;
        }

        return new FunctionOutput([
            EventResult.outputOf(new Map([[Reverse.EVENT_RESULT_NAME, source]])),
        ]);
    }
}
