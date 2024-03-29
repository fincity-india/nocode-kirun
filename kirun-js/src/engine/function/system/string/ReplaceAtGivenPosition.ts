import { Schema } from '../../../json/schema/Schema';
import { Event } from '../../../model/Event';
import { EventResult } from '../../../model/EventResult';
import { FunctionOutput } from '../../../model/FunctionOutput';
import { FunctionSignature } from '../../../model/FunctionSignature';
import { Parameter } from '../../../model/Parameter';
import { Namespaces } from '../../../namespaces/Namespaces';
import { FunctionExecutionParameters } from '../../../runtime/FunctionExecutionParameters';

import { AbstractFunction } from '../../AbstractFunction';

export class ReplaceAtGivenPosition extends AbstractFunction {
    protected static readonly PARAMETER_STRING_NAME: string = 'string';

    protected static readonly PARAMETER_AT_START_NAME: string = 'startPosition';

    protected static readonly PARAMETER_AT_LENGTH_NAME: string = 'lengthPosition';

    protected static readonly PARAMETER_REPLACE_STRING_NAME: string = 'replaceString';

    protected static readonly EVENT_RESULT_NAME: string = 'result';

    protected static PARAMETER_STRING: Parameter = new Parameter(
        ReplaceAtGivenPosition.PARAMETER_STRING_NAME,
        Schema.ofString(ReplaceAtGivenPosition.PARAMETER_STRING_NAME),
    );

    protected static PARAMETER_AT_START: Parameter = new Parameter(
        ReplaceAtGivenPosition.PARAMETER_AT_START_NAME,
        Schema.ofInteger(ReplaceAtGivenPosition.PARAMETER_AT_START_NAME),
    );

    protected static PARAMETER_AT_LENGTH: Parameter = new Parameter(
        ReplaceAtGivenPosition.PARAMETER_AT_LENGTH_NAME,
        Schema.ofInteger(ReplaceAtGivenPosition.PARAMETER_AT_LENGTH_NAME),
    );

    protected static PARAMETER_REPLACE_STRING: Parameter = new Parameter(
        ReplaceAtGivenPosition.PARAMETER_REPLACE_STRING_NAME,
        Schema.ofString(ReplaceAtGivenPosition.PARAMETER_REPLACE_STRING_NAME),
    );

    protected static EVENT_STRING: Event = new Event(
        Event.OUTPUT,
        new Map([
            [
                ReplaceAtGivenPosition.EVENT_RESULT_NAME,
                Schema.ofString(ReplaceAtGivenPosition.EVENT_RESULT_NAME),
            ],
        ]),
    );

    public constructor() {
        super();
    }

    private signature: FunctionSignature = new FunctionSignature('ReplaceAtGivenPosition')
        .setNamespace(Namespaces.STRING)
        .setParameters(
            new Map([
                [
                    ReplaceAtGivenPosition.PARAMETER_STRING.getParameterName(),
                    ReplaceAtGivenPosition.PARAMETER_STRING,
                ],
                [
                    ReplaceAtGivenPosition.PARAMETER_AT_START.getParameterName(),
                    ReplaceAtGivenPosition.PARAMETER_AT_START,
                ],
                [
                    ReplaceAtGivenPosition.PARAMETER_AT_LENGTH.getParameterName(),
                    ReplaceAtGivenPosition.PARAMETER_AT_LENGTH,
                ],
                [
                    ReplaceAtGivenPosition.PARAMETER_REPLACE_STRING.getParameterName(),
                    ReplaceAtGivenPosition.PARAMETER_REPLACE_STRING,
                ],
            ]),
        )
        .setEvents(
            new Map([
                [
                    ReplaceAtGivenPosition.EVENT_STRING.getName(),
                    ReplaceAtGivenPosition.EVENT_STRING,
                ],
            ]),
        );

    public getSignature(): FunctionSignature {
        return this.signature;
    }

    protected async internalExecute(context: FunctionExecutionParameters): Promise<FunctionOutput> {
        let inputString: string = context
            ?.getArguments()
            ?.get(ReplaceAtGivenPosition.PARAMETER_STRING_NAME);
        let startPosition: number = context
            ?.getArguments()
            ?.get(ReplaceAtGivenPosition.PARAMETER_AT_START_NAME);
        let length: number = context
            ?.getArguments()
            ?.get(ReplaceAtGivenPosition.PARAMETER_AT_LENGTH_NAME);
        let replaceString: string = context
            ?.getArguments()
            ?.get(ReplaceAtGivenPosition.PARAMETER_REPLACE_STRING_NAME);
        let inputStringLength: number = inputString.length;

        if (startPosition < length) {
            let outputString: string = '';
            outputString += inputString.substring(0, startPosition);
            outputString += replaceString;
            outputString += inputString.substring(startPosition + length);
        }

        return new FunctionOutput([
            EventResult.outputOf(
                new Map([[ReplaceAtGivenPosition.EVENT_RESULT_NAME, inputString]]),
            ),
        ]);
    }
}
