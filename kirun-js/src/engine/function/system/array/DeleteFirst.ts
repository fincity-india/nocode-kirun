import { KIRuntimeException } from '../../../exception/KIRuntimeException';
import { EventResult } from '../../../model/EventResult';
import { FunctionOutput } from '../../../model/FunctionOutput';
import { FunctionExecutionParameters } from '../../../runtime/FunctionExecutionParameters';
import { AbstractArrayFunction } from './AbstractArrayFunction';

export class DeleteFirst extends AbstractArrayFunction {
    public constructor() {
        super('DeleteFirst', [DeleteFirst.PARAMETER_ARRAY_SOURCE], DeleteFirst.EVENT_RESULT_ARRAY);
    }

    protected async internalExecute(context: FunctionExecutionParameters): Promise<FunctionOutput> {
        let source: any[] = context
            ?.getArguments()
            ?.get(DeleteFirst.PARAMETER_ARRAY_SOURCE.getParameterName());

        if (source.length == 0) throw new KIRuntimeException('Given source array is empty');

        source = [...source];
        source.shift();
        return new FunctionOutput([
            EventResult.outputOf(new Map([[AbstractArrayFunction.EVENT_RESULT_NAME, source]])),
        ]);
    }
}
