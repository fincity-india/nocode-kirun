import { KIRuntimeException } from '../../../exception/KIRuntimeException';
import { EventResult } from '../../../model/EventResult';
import { FunctionOutput } from '../../../model/FunctionOutput';
import { FunctionExecutionParameters } from '../../../runtime/FunctionExecutionParameters';
import { AbstractArrayFunction } from './AbstractArrayFunction';

export class IndexOf extends AbstractArrayFunction {
    public constructor() {
        super(
            'IndexOf',
            [
                IndexOf.PARAMETER_ARRAY_SOURCE,
                IndexOf.PARAMETER_ANY_NOT_NULL,
                IndexOf.PARAMETER_INT_FIND_FROM,
            ],
            IndexOf.EVENT_RESULT_INTEGER,
        );
    }

    protected internalExecute(context: FunctionExecutionParameters): FunctionOutput {
        let source: any[] = context
            .getArguments()
            .get(IndexOf.PARAMETER_ARRAY_SOURCE.getParameterName());

        var find = context.getArguments().get(IndexOf.PARAMETER_ANY_NOT_NULL.getParameterName());

        let len: number = context
            .getArguments()
            .get(IndexOf.PARAMETER_INT_FIND_FROM.getParameterName());

        if (source.length == 0)
            return new FunctionOutput([
                EventResult.outputOf(new Map([[IndexOf.EVENT_RESULT_INTEGER.getName(), -1]])),
            ]);

        if (typeof find == null || typeof find == undefined)
            throw new KIRuntimeException(
                'Please provide the valid find object or primitive in order to verify',
            );

        if (len < 0 || len > source.length)
            throw new KIRuntimeException(
                'The size of the search index of the array is greater than the size of the array',
            );

        let index: number = -1;

        for (let i: number = len; i < source.length; i++) {
            if (source[i] == find) {
                index = i;
                break;
            }
        }

        return new FunctionOutput([
            EventResult.outputOf(new Map([[IndexOf.EVENT_RESULT_INTEGER.getName(), index]])),
        ]);
    }
}