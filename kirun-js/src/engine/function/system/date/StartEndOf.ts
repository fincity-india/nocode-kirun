import { DurationLikeObject } from 'luxon';
import { Schema } from '../../../json/schema/Schema';
import { EventResult } from '../../../model/EventResult';
import { FunctionOutput } from '../../../model/FunctionOutput';
import { Parameter } from '../../../model/Parameter';
import { FunctionExecutionParameters } from '../../../runtime/FunctionExecutionParameters';
import { MapUtil } from '../../../util/MapUtil';
import { AbstractDateFunction } from './AbstractDateFunction';
import { getDateTime } from './common';

export class StartEndOf extends AbstractDateFunction {
    private readonly isStart: boolean;

    constructor(isStart: boolean) {
        super(
            isStart ? 'StartOf' : 'EndOf',
            AbstractDateFunction.EVENT_TIMESTAMP,
            AbstractDateFunction.PARAMETER_TIMESTAMP,
            AbstractDateFunction.PARAMETER_UNIT,
        );

        this.isStart = isStart;
    }
    protected async internalExecute(context: FunctionExecutionParameters): Promise<FunctionOutput> {
        const timestamp = context
            .getArguments()
            ?.get(AbstractDateFunction.PARAMETER_TIMESTAMP_NAME);

        const dateTime = getDateTime(timestamp);

        const unit = context
            .getArguments()
            ?.get(AbstractDateFunction.PARAMETER_UNIT_NAME)
            ?.toLowerCase();

        const newDateTime = this.isStart ? dateTime.startOf(unit) : dateTime.endOf(unit);

        return new FunctionOutput([
            EventResult.outputOf(
                MapUtil.of(AbstractDateFunction.EVENT_TIMESTAMP_NAME, newDateTime.toISO()),
            ),
        ]);
    }
}