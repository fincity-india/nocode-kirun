import { Schema } from '../../../json/schema/Schema';
import { Event } from '../../../model/Event';
import { EventResult } from '../../../model/EventResult';
import { FunctionOutput } from '../../../model/FunctionOutput';
import { FunctionSignature } from '../../../model/FunctionSignature';
import { Parameter } from '../../../model/Parameter';
import { Namespaces } from '../../../namespaces/Namespaces';
import { FunctionExecutionParameters } from '../../../runtime/FunctionExecutionParameters';
import { AbstractFunction } from '../../AbstractFunction';

export class DeleteForGivenLength extends AbstractFunction {
    public static readonly PARAMETER_STRING_NAME: string = 'string';

    public static readonly PARAMETER_AT_START_NAME: string = 'startPosition';

    public static readonly PARAMETER_AT_END_NAME: string = 'endPosition';

    public static readonly EVENT_RESULT_NAME: string = 'result';

    protected readonly PARAMETER_STRING: Parameter = new Parameter(
        DeleteForGivenLength.PARAMETER_STRING_NAME,
        Schema.ofString(DeleteForGivenLength.PARAMETER_STRING_NAME),
    );

    protected readonly PARAMETER_AT_START: Parameter = new Parameter(
        DeleteForGivenLength.PARAMETER_AT_START_NAME,
        Schema.ofInteger(DeleteForGivenLength.PARAMETER_AT_START_NAME),
    );

    protected readonly PARAMETER_AT_END: Parameter = new Parameter(
        DeleteForGivenLength.PARAMETER_AT_END_NAME,
        Schema.ofInteger(DeleteForGivenLength.PARAMETER_AT_END_NAME),
    );

    protected readonly EVENT_STRING: Event = new Event(
        Event.OUTPUT,
        new Map([
            [
                DeleteForGivenLength.EVENT_RESULT_NAME,
                Schema.ofString(DeleteForGivenLength.EVENT_RESULT_NAME),
            ],
        ]),
    );

    private signature: FunctionSignature = new FunctionSignature('DeleteForGivenLength')
        .setNamespace(Namespaces.STRING)
        .setParameters(
            new Map([
                [this.PARAMETER_STRING.getParameterName(), this.PARAMETER_STRING],
                [this.PARAMETER_AT_START.getParameterName(), this.PARAMETER_AT_START],
                [this.PARAMETER_AT_END.getParameterName(), this.PARAMETER_AT_END],
            ]),
        )
        .setEvents(new Map([[this.EVENT_STRING.getName(), this.EVENT_STRING]]));

    public constructor() {
        super();
    }

    public getSignature(): FunctionSignature {
        return this.signature;
    }

    protected async internalExecute(context: FunctionExecutionParameters): Promise<FunctionOutput> {
        let inputString: string = context
            ?.getArguments()
            ?.get(DeleteForGivenLength.PARAMETER_STRING_NAME);
        let startPosition: number = context
            ?.getArguments()
            ?.get(DeleteForGivenLength.PARAMETER_AT_START_NAME);
        let endPosition: number = context
            ?.getArguments()
            ?.get(DeleteForGivenLength.PARAMETER_AT_END_NAME);

        if (endPosition >= startPosition) {
            let outputString: string = '';

            outputString += inputString.substring(0, startPosition);
            outputString += inputString.substring(endPosition);

            return new FunctionOutput([
                EventResult.outputOf(
                    new Map([[DeleteForGivenLength.EVENT_RESULT_NAME, outputString.toString()]]),
                ),
            ]);
        }

        return new FunctionOutput([
            EventResult.outputOf(new Map([[DeleteForGivenLength.EVENT_RESULT_NAME, inputString]])),
        ]);
    }
}
