import { KIRunSchemaRepository } from '../../../../../src/engine/repository/KIRunSchemaRepository';
import { KIRunFunctionRepository } from '../../../../../src/engine/repository/KIRunFunctionRepository';
import { FunctionExecutionParameters, MapUtil } from '../../../../../src';
import { AbstractDateFunction } from '../../../../../src/engine/function/system/date/AbstractDateFunction';
import { StartEndOf } from '../../../../../src/engine/function/system/date/StartEndOf';
import { Settings } from 'luxon';

Settings.defaultZone = 'UTC+05:30';

const startOf = new StartEndOf(true);
const endOf = new StartEndOf(false);

describe('StartEndOf', () => {
    test('should return the start of the year', async () => {
        const fep = new FunctionExecutionParameters(
            new KIRunFunctionRepository(),
            new KIRunSchemaRepository(),
        ).setArguments(
            MapUtil.of(
                AbstractDateFunction.PARAMETER_TIMESTAMP_NAME,
                '2023-12-31T22:00:00.000Z',
                AbstractDateFunction.PARAMETER_UNIT_NAME,
                'YEAR',
            ),
        );

        const result = await startOf.execute(fep);

        expect(
            result.allResults()[0].getResult().get(AbstractDateFunction.EVENT_TIMESTAMP_NAME),
        ).toBe('2024-01-01T00:00:00.000+05:30');
    });

    test('should return the end of the year', async () => {
        const fep = new FunctionExecutionParameters(
            new KIRunFunctionRepository(),
            new KIRunSchemaRepository(),
        ).setArguments(
            MapUtil.of(
                AbstractDateFunction.PARAMETER_TIMESTAMP_NAME,
                '2023-12-31T22:00:00.000Z',
                AbstractDateFunction.PARAMETER_UNIT_NAME,
                'YEAR',
            ),
        );

        const result = await endOf.execute(fep);

        expect(
            result.allResults()[0].getResult().get(AbstractDateFunction.EVENT_TIMESTAMP_NAME),
        ).toBe('2024-12-31T23:59:59.999+05:30');
    });
});